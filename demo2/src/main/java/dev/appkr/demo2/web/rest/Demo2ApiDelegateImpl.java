package dev.appkr.demo2.web.rest;

import dev.appkr.demo2.web.api.Demo2ApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Demo2ApiDelegateImpl implements Demo2ApiDelegate {

  @Override
  public ResponseEntity<String> demo2() {
    return ResponseEntity.ok("This is demo2");
  }
}
