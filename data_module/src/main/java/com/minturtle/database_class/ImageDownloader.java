package com.minturtle.database_class;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.apache.commons.io.FileUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


public class ImageDownloader {
    public static void downloadImage(String searchQuery) throws IOException{
        File dir = new File("images/" + searchQuery);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        else{
            return;
        }


        final String encodedSearchQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8.toString());
        trustAllCertificates();
        String cx = "07ad331604ac54edb"; // 구글 커스텀 검색 엔진 ID
        String apiKey = "AIzaSyCh1BMNsAlTBqIqz_XozlyyEB-4Tco1DgQ"; // 구글 API 키

        String urlString = "https://www.googleapis.com/customsearch/v1?q=" + encodedSearchQuery + "&cx=" + cx + "&searchType=image&key=" + apiKey;
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        String json = sb.toString();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray items = jsonObject.getAsJsonArray("items");


        int count = 0;
        int i = 0;
        while(count < 3){
            try {
                if(items.size() < i) return;

                String link = items.get(i).getAsJsonObject().get("link").getAsString();
                // Download image into the created directory
                FileUtils.copyURLToFile(new URL(link), new File(dir, "image" + i + ".jpg"));
                count++;
            }catch (Exception e){ e.printStackTrace();}

            finally {
                i++;
            }

        }
    }


    private static void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
