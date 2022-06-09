package org.nwolfhub;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.cli.*;
import org.nwolfhub.vkUser.Vk;
import org.nwolfhub.vkUser.requests.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        Options options = new Options();

        Option tokenOption = new Option("t", "token", true, "Token of a user");
        tokenOption.setRequired(true);
        options.addOption(tokenOption);

        Option startOption = new Option("s", "start", true, "Start id of a group");
        startOption.setRequired(true);
        options.addOption(startOption);

        Option endOption = new Option("a", "amount", true, "Amount of groups to find");
        endOption.setRequired(true);
        options.addOption(endOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        try  {
            CommandLine cmd = parser.parse(options, args);
            String token = cmd.getOptionValue("token");
            Integer start = Integer.valueOf(cmd.getOptionValue("start"));
            Integer amount = Integer.valueOf(cmd.getOptionValue("amount"));
            run(token, start, amount);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(String token, Integer start, Integer amount) throws IOException {
        Long begin = System.currentTimeMillis();
        Vk vk = new Vk(token);
        StringBuilder builder = new StringBuilder();
        for (int now = 0; now<amount; now++) {
            builder.append(start);
            if(now!=amount-1) {
                builder.append(",");
            }
            start++;
        }
        String res = vk.makeRequest(new Request(vk, "groups.getById", "group_ids=" + builder));
        JsonArray array = JsonParser.parseString(res).getAsJsonObject().get("response").getAsJsonArray();
        StringBuilder printable = new StringBuilder();
        for(JsonElement obj:array) {
            printable.append(obj.getAsJsonObject().get("name").getAsString()).append(" ".repeat(80-("https://vk.com/club" + obj.getAsJsonObject().get("id").getAsString()).length())).append(" https://vk.com/club").append(obj.getAsJsonObject().get("id").getAsString()).append("\n");
        }
        File output = new File("out.txt");
        if(!output.exists()) output.createNewFile();
        FileOutputStream out = new FileOutputStream(output);
        out.write(printable.toString().getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
        System.out.println(printable);
        System.out.println("Took " + (System.currentTimeMillis() - begin) + "ms");
    }
}
