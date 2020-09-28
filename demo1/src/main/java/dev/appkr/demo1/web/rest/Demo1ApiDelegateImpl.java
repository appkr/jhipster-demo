package dev.appkr.demo1.web.rest;

import dev.appkr.demo1.web.api.Demo1ApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Demo1ApiDelegateImpl implements Demo1ApiDelegate {

  @Override
  public ResponseEntity<String> demo1() {
    return ResponseEntity.ok("This is demo1");
  }
}
