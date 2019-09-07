$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("mobileDemo.feature");
formatter.feature({
  "line": 2,
  "name": "mobile demo",
  "description": "",
  "id": "mobile-demo",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@mobileDemo"
    }
  ]
});
formatter.scenario({
  "line": 4,
  "name": "small feature",
  "description": "",
  "id": "mobile-demo;small-feature",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "I click on login button",
  "keyword": "When "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});