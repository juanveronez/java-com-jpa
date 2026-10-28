package br.com.alura.screenmatch.service;


import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputText;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model("gpt-6-luna")
                .input("traduza para o português o texto: " + texto)
                .maxOutputTokens(1000)
                .build();

        Response response = client.responses().create(params);

        return response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(ResponseOutputText::text)
                .reduce("", String::concat);
    }
}