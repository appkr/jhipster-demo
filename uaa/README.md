## jhipster-uaa

Original Jhipster UAA 3.9.0

### Prepare MySQL

```sql
-- mysql user: root / secret
create database if not exists `jhipster_uaa` default character set = utf8 default collate = utf8_general_ci;
```

### Run

```bash
$ ./gradlew clean bootRun
```

### Test

| 구분 | 엔드포인트              |
| ---- | ----------------------- |
| 토큰 | `POST /oauth/token`     |
| 키   | `POST /oauth/token_key` |

| grant_type         | clientId | clientSecret |
| ------------------ | -------- | ------------ |
| client_credentials | internal | internal     |
| password           | web_app  | changeit     |

| username | password |
| -------- | -------- |
| user     | user     |

#### CLIENT_CREDENTIALS grant

```bash
$ curl -s -X POST --data "grant_type=client_credentials" http://internal:internal@localhost:9999/oauth/token

{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJ3ZWItYXBwIl0sImV4cCI6MTYwNzA5MjIyNywiaWF0IjoxNjA3MDkwNDI3LCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6InFxUWNGNVhVcWkyVWRjYV9lcC12QzQwRHRsTSIsImNsaWVudF9pZCI6ImludGVybmFsIn0.DRZiFgPjORGg6HVxOlO3fDPBsoHMSOFMc58JGisGlXKUvJR37IOa43DL6j6juVsGPrljVxJ7ymxXUlt_mw_ewIRW2Fury_gnmU4WzP7-rG6XtDs1GD71FbiOGSDtGRt1HQXPQ946FoSEQIGEcwDvbBLbkShgDpHkWMuN0BQCRrpnMdW1JE5xKwnUU-Q_yVknGoMe9zJuHtQIGB6bui6dRjaeBCruJBC5l8_5ZobRfmr28CaCypBs-0sNEgQjGBHqHnU6LWDccH6LKQunGjCoOIzKJ3EQzaQaTNot2eEiZerEyDooil0AF5hX67UrQIb5QmeeinYH8i9RVndoepDkXg",
  "token_type": "bearer",
  "expires_in": 1799,
  "scope": "web-app",
  "iat": 1607090427,
  "jti": "qqQcF5XUqi2Udca_ep-vC40DtlM"
}
```

#### PASSWORD grant

```bash
$ curl -s -X POST --data "username=user&password=user&grant_type=password&scope=openid" http://web_app:changeit@localhost:9999/oauth/token

{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsib3BlbmlkIl0sImV4cCI6MTYwNzA5MDc5MiwiaWF0IjoxNjA3MDkwNDkyLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiX3AtV2hhaDdIU29rdTl5ZTZobkM3aWdrcnBnIiwiY2xpZW50X2lkIjoid2ViX2FwcCJ9.ClbqwldyqnVKb0F7BYw8ZBkv7dbBAsK9PzwuX1P381bVm-IxfSYBKM0BzhyiOgdA8Mv3s0Ysh2FWbINDmmyeRA6gah8qjGg2Z_vyllL7odjblEciSbAg8Q8frOcd0kbvWTnMCg4dRfyncfOWVHmQuHe4Zr2UHcaJ0w3PL8KDQkJ7eAredWwjIHXtSILqP_o3KCn28TWOPa54lEl6qTjzpTghMO_aXlhiktD41iDkLfYroTzNcZJlsLImsHoU6BFCJEY1p_To0cyJN4sGtJ8mcWnsZ1xz-RFXqGHCcIWbe1i1TlY57zRy3LhdcvtQYSqVKXnCsFYPig05-FbN9lMgCg",
  "token_type": "bearer",
  "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsib3BlbmlkIl0sImF0aSI6Il9wLVdoYWg3SFNva3U5eWU2aG5DN2lna3JwZyIsImV4cCI6MTYwNzY5NTI5MiwiaWF0IjoxNjA3MDkwNDkyLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoicEctaW1UX3d3dGhaamtEMWxmdVM0ai15OUxvIiwiY2xpZW50X2lkIjoid2ViX2FwcCJ9.djdfVjWB4zH2OCoB8aVUbCTg9Qq7vbAQr3occZ7a2BG1kzLGhYBbiC4gKJj2EcSP06Ewly--z9-lcse8u3f-qJGX893DjuFXAAyMvGs0J-aGZpOYL3HjvJWzKE-ecSI4AYlE0hih3fL5x1ZEj6gsCs-CpUaROuWJkGGT7TTxi-YB8k3IuqQczN9q4qFjRDuxY7mFdVv7J8RfKmYViLQrYvL3n39r91iLbxjRDP2lDUw0kwVAbibAp6_SC981kVVEHdSk7p64kjwFG2jBpyvM2Cv2g2UttDrPpppxCICOFFNXOHSJoxLoN1KXmtT7qc0_GAiBqksIY1RlXyz0lHQ0dA",
  "expires_in": 299,
  "scope": "openid",
  "iat": 1607090492,
  "jti": "_p-Whah7HSoku9ye6hnC7igkrpg"
}
```

#### TOKEN KEY

```bash
$ curl -s http://localhost:9999/oauth/token_key

{
  "alg": "SHA256withRSA",
  "value": "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlo/L8mU9Isiihp1ksxeOrJzPn4915xZC/pnbO+ur/ccZL23BYHP/wUxpWZy8Gh94+GK8/gcjVEk66acg4Gk7NH0uQGxdrq8WDMywPIAawekwiQJd6l/yVNXIDhuk0LzcgmU+1ESyeTNdlx84Z0X3HI6w8SH6OE4RBcr9rGfIt0ytXmHj1P4zxmJt/YhZyyyUq0WGuBq31UaQTOiJa0rp1kDKSMN0Hvz4UmkYtUvgtqUujrqNcWkSEummO8WyuhnCs+zAaF2KA5XSalEXFNiILwFPtQFxqIQrjjiWcI61vvTxtor4zI5r4X6aDteYIJidAzYwkIiuacnLWX5ziL3j+wIDAQAB\n-----END PUBLIC KEY-----"
}
```
