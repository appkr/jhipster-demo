package dev.appkr.demo1.web.rest;

import dev.appkr.demo1.web.api.Demo1ApiDelegate;
import dev.appkr.demo2.java.api.Demo2Api;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
public class Demo1ApiDelegateImpl implements Demo1ApiDelegate {

  private final Demo2Api demo2Api;

  public Demo1ApiDelegateImpl(Demo2Api demo2Api) {
    this.demo2Api = demo2Api;
  }

  @Override
  public ResponseEntity<String> demo1() {
    String message = "This is demo1";

    try {
      message = demo2Api.demo2();
    } catch (RestClientException e) {
      e.printStackTrace();
    }

    return ResponseEntity.ok(message);
  }
}
