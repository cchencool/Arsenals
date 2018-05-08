package com.inspur.httpabout;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class httpRequestTest {

    public static String url = "http://localhost:8080/init2?name=cc&&time=003";

    public static void doRequest() {

        try {
            HttpClient client = HttpClientBuilder.create().build();

            HttpPost request = new HttpPost(url);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                String resultStr = EntityUtils.toString(response.getEntity());

                System.out.println(resultStr);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        doRequest();

    }

}


// in python script

/*

# -*- coding: utf-8 -*-

import requests

url = "http://localhost:8080/init2?name=cc&&time=003"

res = requests.get(url)

print(res.content.decode('utf-8'))

 */
