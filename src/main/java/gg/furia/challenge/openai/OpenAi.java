package gg.furia.challenge.openai;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.furia.challenge.chatbot.discord.util.MessageUtil;
import gg.furia.challenge.config.Config;
import gg.furia.challenge.config.YamlUtil;
import gg.furia.challenge.exception.InvalidResponseException;
import gg.furia.challenge.exception.OpenAiException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Slf4j
public class OpenAi {
    private String apiKey;
    private URI uri;
    private String model;

    @Getter(AccessLevel.NONE)
    private Config config;

    public OpenAi() {
        Config.OpenAIConfig config = YamlUtil.getConfig().getOpenai();
        this.apiKey = config.getToken();
        this.model = config.getModel();
        this.uri = URI.create("https://api.openai.com/v1/chat/completions");
        this.config = YamlUtil.getConfig();
    }

    /**
     * Generates text using the OpenAI API.
     * @param roleMessage A list of messages to be sent to the API.
     * @param username The username of the user making the request.
     * @return The generated text from the API.
     * @throws OpenAiException If an error occurs while sending the request to the API.
     */
    public String generateText(List<MessageUtil.RoleMessage> roleMessage, String username) throws OpenAiException {
        JsonObject requestBody = prepareRequestBody(roleMessage, username);
        String body = requestBody.toString();

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build()) {

            HttpRequest request = buildRequest(body);
            response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (response.statusCode() != 200) {
                throw new InvalidResponseException("API returned status code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            throw new OpenAiException("Error while sending request to OpenAI API", e);
        }



        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray choices = jsonResponse.getAsJsonArray("choices");

        if (choices == null || choices.isEmpty()) {
            throw new InvalidResponseException("OpenAI API response does not contain choices");
        }

        JsonObject firstChoice = choices.get(0).getAsJsonObject();

        return firstChoice
                .getAsJsonObject("message")
                .get("content").getAsString();
    }

    /**
     * Opens a connection to the OpenAI API with the specified request type and API key.
     *
     * @return HttpsURLConnection object representing the connection to the OpenAI API.
     */
    private HttpRequest buildRequest(String body) {

        return HttpRequest.newBuilder(uri)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Content-Encoding", "UTF-8")
                .header("Accept", "application/json; UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
    }

    /**
     * Prepares the request body for the OpenAI API.
     *
     * @param messages A LinkedHashMap containing the messages to be sent to the API.
     * @param username The username of the user making the request.
     * @return JsonObject representing the request body.
     */
    private JsonObject prepareRequestBody(List<MessageUtil.RoleMessage> messages, String username) {
        JsonObject requestBody = new JsonObject();
        JsonArray messagesJson = new JsonArray();

        requestBody.addProperty("model", model);

        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        String systemMessageContent = YamlUtil.getConfig().getOpenai().getSystemMessage();

        log.info("User: {}, Message {}", username, systemMessageContent);

        if (systemMessageContent.contains("${user}")) {
            systemMessageContent = systemMessageContent.replace("${user}", username);
        }

        systemMessage.addProperty("content", systemMessageContent);
        messagesJson.add(systemMessage);

        for (MessageUtil.RoleMessage message : messages) {
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", message.isFromBot() ? "assistant" : "user");
            userMessage.addProperty("content", message.content());
            messagesJson.add(userMessage);
        }

        requestBody.add("messages", messagesJson);
        return requestBody;
    }
}
