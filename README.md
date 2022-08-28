# truecaller-prefixes-match

- problem statement: https://github.com/roottraveller/truecaller-prefixes-match/blob/master/longest-prefix-matches.pdf
- prefix data in present at path resources/data/prefix-data.txt
- Swagger UI: http://localhost:8080/swagger-ui.html
- build project
  ```
  ./gradlew clean build 
  ```
- run project
    - goto PrefixesMatchApplication.java and run

### Play with code

- step 1: ingest prefixes (or use POSTMAN to ingest bulk prefix data present at path resources/data/prefix-data.txt)
  ```base
  $ curl -X POST "http://localhost:8080/api/truecaller/index" -H "accept: */*" -H "Content-Type: text/plain" 
  -d '2y3fKTS
    4VdwEEXC8
    AWMa4vvOf
    xyiuoXZV
    KAWeqI
    XfhdW
    8dLfGZU0T
    LgXK8iwln
    oDyiYa
    2y3fKTa
    KAWeqD
    KAWeq7
    Rhp1IlD
    Xfhdd
    KAWeq9
    XYfxPGW
    Uvc5oJ
    NxJ7fbA'
  ```
    - response
  ```base
  {
  "success": 18,
  "failed": 0
  }
  ```


- step 2: search the longest prefix
    - curl 1
      ```base
      $ curl -X GET "http://localhost:8080/api/truecaller/search/KAWeqIIamBatman?partial=false" -H "accept: */*"
      ```
        - response
      ```base
      {
      "prefix": "KAWeqI",
      "matched": "FULL"
      }
      ```
    - curl 2
      ```base
      $ curl -X GET "http://localhost:8080/api/truecaller/search/AKAWeqIIamBatman?partial=true" -H "accept: */*"
      ```
        - response
      ```base
      {
      "prefix": "",
      "matched": "NONE"
      }
      ```
    - curl 3
      ```base
      $ curl -X GET "http://localhost:8080/api/truecaller/search/KAWeIambatmanqIIamBatman?partial=true" -H "accept: */*"
      ```
        - response
      ```base
      {
      "prefix": "KAWe",
      "matched": "PARTIAL"
      }
      ```
    - curl 4
      ```base
      $ curl -X GET "http://localhost:8080/api/truecaller/search/KAWeIambatmanqIIamBatman?partial=false" -H "accept: */*"
      ```
        - response
      ```base
      {
      "prefix": "",
      "matched": "NONE"
      }
      ```
