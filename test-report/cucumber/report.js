$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("EndToEndScenario.feature");
formatter.feature({
  "line": 2,
  "name": "Flight Booking",
  "description": "Do Flight booking with Round Trip Flight for one passengers from London to Paris on dates 18 Jan Departure \r\nand 28th Jan Arrival and with Economy class",
  "id": "flight-booking",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@EndToEnd"
    }
  ]
});
formatter.scenario({
  "line": 6,
  "name": "",
  "description": "I book flight Round Trip Flight for one passengers from London to Paris on dates 18 Jan Departure and 28th Jan Arrival and with Economy class",
  "id": "flight-booking;",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 8,
  "name": "I launch website for Mercury Tours",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "I click on SIGN ON link",
  "keyword": "Then "
});
formatter.step({
  "line": 11,
  "name": "I enter username and password and click on continue button",
  "rows": [
    {
      "cells": [
        "Username",
        "Password"
      ],
      "line": 12
    },
    {
      "cells": [
        "kateakash",
        "kateakash"
      ],
      "line": 13
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 15,
  "name": "I enter flight details along with perferences",
  "rows": [
    {
      "cells": [
        "tripType",
        "noOfPassengers",
        "departureFrom",
        "arrivingIn",
        "departureMonth",
        "departureDate",
        "arrivingMonth",
        "arrivingDate",
        "airline",
        "serviceClass"
      ],
      "line": 16
    },
    {
      "cells": [
        "roundTrip",
        "1",
        "London",
        "Paris",
        "January",
        "18",
        "January",
        "28",
        "Unified Airlines",
        "coach"
      ],
      "line": 17
    }
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 19,
  "name": "I select flight and click on continue button",
  "keyword": "Then "
});
formatter.step({
  "line": 21,
  "name": "I enter details of passengers",
  "rows": [
    {
      "cells": [
        "firstName",
        "lastName",
        "meal"
      ],
      "line": 22
    },
    {
      "cells": [
        "akash",
        "kate",
        "Vegetarian"
      ],
      "line": 23
    }
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 25,
  "name": "I enter details for CC and Billing address and secure booking",
  "rows": [
    {
      "cells": [
        "cardType",
        "cardNumber",
        "expMonth",
        "expYear",
        "ccfirstName",
        "ccmiddleName",
        "cclastName",
        "ccaddress",
        "cccity",
        "ccstate",
        "ccpostalcode",
        "cccountry"
      ],
      "line": 26
    },
    {
      "cells": [
        "Visa",
        "123123123",
        "10",
        "2010",
        "Soham",
        "Akash",
        "Kate",
        "Banglore",
        "Banglore",
        "Banglore",
        "212323",
        "INDIA"
      ],
      "line": 27
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 29,
  "name": "I validate all details on summary page",
  "keyword": "Then "
});
formatter.step({
  "line": 30,
  "name": "I click on Logg Off button",
  "keyword": "And "
});
formatter.match({
  "location": "LoginSteps.launch_website_for_mercury_tours()"
});
formatter.result({
  "duration": 7112060169,
  "status": "passed"
});
formatter.match({
  "location": "LoginSteps.click_sign_on()"
});
formatter.result({
  "duration": 2018581311,
  "status": "passed"
});
formatter.match({
  "location": "LoginSteps.enter_username_password_continue_with_login_withDataTable(DataTable)"
});
formatter.result({
  "duration": 8283380164,
  "status": "passed"
});
formatter.match({
  "location": "FlightFinderSteps.enter_flight_details_along_with_preferencessss(JournyDetails\u003e)"
});
formatter.result({
  "duration": 10413225748,
  "status": "passed"
});
formatter.match({
  "location": "SelectFlightSteps.select_default_flight()"
});
formatter.result({
  "duration": 3693553608,
  "status": "passed"
});
formatter.match({
  "location": "PaymentSteps.enter_passengers_details(PassengerDetails\u003e)"
});
formatter.result({
  "duration": 5869422109,
  "status": "passed"
});
formatter.match({
  "location": "PaymentSteps.enter_ccdetails_and_billingaddress(CreditCardDetails\u003e)"
});
formatter.result({
  "duration": 21799348349,
  "status": "passed"
});
formatter.match({
  "location": "SummarySteps.validate_details_on_summary_page()"
});
formatter.result({
  "duration": 10647474895,
  "status": "passed"
});
formatter.match({
  "location": "SummarySteps.click_logoff_btn()"
});
formatter.result({
  "duration": 2405934270,
  "status": "passed"
});
formatter.scenario({
  "line": 34,
  "name": "",
  "description": "I book flight Round Trip Flight for one passengers from New York to Sydney on dates 24 Jan Departure and 31th Jan Arrival and with First class",
  "id": "flight-booking;",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 36,
  "name": "I launch website for Mercury Tours",
  "keyword": "Given "
});
formatter.step({
  "line": 38,
  "name": "I click on SIGN ON link",
  "keyword": "Then "
});
formatter.step({
  "line": 39,
  "name": "I enter username and password and click on continue button",
  "rows": [
    {
      "cells": [
        "Username",
        "Password"
      ],
      "line": 40
    },
    {
      "cells": [
        "kateakash",
        "kateakash"
      ],
      "line": 41
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 43,
  "name": "I enter flight details along with perferences",
  "rows": [
    {
      "cells": [
        "tripType",
        "noOfPassengers",
        "departureFrom",
        "arrivingIn",
        "departureMonth",
        "departureDate",
        "arrivingMonth",
        "arrivingDate",
        "airline",
        "serviceClass"
      ],
      "line": 44
    },
    {
      "cells": [
        "roundTrip",
        "1",
        "New York",
        "Sydney",
        "January",
        "24",
        "January",
        "31",
        "Blue Skies Airlines",
        "first"
      ],
      "line": 45
    }
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 47,
  "name": "I select flight and click on continue button",
  "keyword": "Then "
});
formatter.step({
  "line": 49,
  "name": "I enter details of passengers",
  "rows": [
    {
      "cells": [
        "firstName",
        "lastName",
        "meal"
      ],
      "line": 50
    },
    {
      "cells": [
        "vaishali",
        "kate",
        "Vegetarian"
      ],
      "line": 51
    }
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 53,
  "name": "I enter details for CC and Billing address and secure booking",
  "rows": [
    {
      "cells": [
        "cardType",
        "cardNumber",
        "expMonth",
        "expYear",
        "ccfirstName",
        "ccmiddleName",
        "cclastName",
        "ccaddress",
        "cccity",
        "ccstate",
        "ccpostalcode",
        "cccountry"
      ],
      "line": 54
    },
    {
      "cells": [
        "Visa",
        "123123123",
        "10",
        "2010",
        "Akash",
        "Anil",
        "Kate",
        "Pune",
        "Pune",
        "Maharashtra",
        "212323",
        "INDIA"
      ],
      "line": 55
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 57,
  "name": "I validate all details on summary page",
  "keyword": "Then "
});
formatter.step({
  "line": 58,
  "name": "I click on Logg Off button",
  "keyword": "And "
});
formatter.match({
  "location": "LoginSteps.launch_website_for_mercury_tours()"
});
formatter.result({
  "duration": 1083375076,
  "status": "passed"
});
formatter.match({
  "location": "LoginSteps.click_sign_on()"
});
formatter.result({
  "duration": 1601479055,
  "status": "passed"
});
formatter.match({
  "location": "LoginSteps.enter_username_password_continue_with_login_withDataTable(DataTable)"
});
formatter.result({
  "duration": 6741578985,
  "status": "passed"
});
formatter.match({
  "location": "FlightFinderSteps.enter_flight_details_along_with_preferencessss(JournyDetails\u003e)"
});
formatter.result({
  "duration": 10487285965,
  "status": "passed"
});
formatter.match({
  "location": "SelectFlightSteps.select_default_flight()"
});
formatter.result({
  "duration": 3414081541,
  "status": "passed"
});
formatter.match({
  "location": "PaymentSteps.enter_passengers_details(PassengerDetails\u003e)"
});
formatter.result({
  "duration": 5774536073,
  "status": "passed"
});
formatter.match({
  "location": "PaymentSteps.enter_ccdetails_and_billingaddress(CreditCardDetails\u003e)"
});
formatter.result({
  "duration": 19236666786,
  "status": "passed"
});
formatter.match({
  "location": "SummarySteps.validate_details_on_summary_page()"
});
formatter.result({
  "duration": 10630185891,
  "status": "passed"
});
formatter.match({
  "location": "SummarySteps.click_logoff_btn()"
});
formatter.result({
  "duration": 4614057090,
  "status": "passed"
});
formatter.uri("UserRegistration.feature");
formatter.feature({
  "line": 2,
  "name": "User Registration",
  "description": "I fill all details for user and do registration",
  "id": "user-registration",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@Registration"
    }
  ]
});
formatter.scenarioOutline({
  "line": 5,
  "name": "I fill all details for user and do registration",
  "description": "",
  "id": "user-registration;i-fill-all-details-for-user-and-do-registration",
  "type": "scenario_outline",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 6,
  "name": "I launch website \"\u003cApplicationURL\u003e\"",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "I click on Register link",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "I enter new user details for registration",
  "rows": [
    {
      "cells": [
        "firstName",
        "lastName",
        "phonenumber",
        "email",
        "address1",
        "city",
        "state",
        "postalcode",
        "country",
        "userName",
        "password",
        "conformPassword"
      ],
      "line": 9
    },
    {
      "cells": [
        "akash",
        "kate",
        "1231231231",
        "kateakash@gmail.com",
        "Banglore",
        "Banglore",
        "Banglore",
        "411019",
        "INDIA",
        "kateakash",
        "kateakash",
        "kateakash"
      ],
      "line": 10
    }
  ],
  "keyword": "And "
});
formatter.examples({
  "line": 13,
  "name": "",
  "description": "",
  "id": "user-registration;i-fill-all-details-for-user-and-do-registration;",
  "rows": [
    {
      "cells": [
        "ApplicationURL"
      ],
      "line": 14,
      "id": "user-registration;i-fill-all-details-for-user-and-do-registration;;1"
    },
    {
      "cells": [
        "http://newtours.demoaut.com/mercuryreservation.php"
      ],
      "line": 15,
      "id": "user-registration;i-fill-all-details-for-user-and-do-registration;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.scenario({
  "line": 15,
  "name": "I fill all details for user and do registration",
  "description": "",
  "id": "user-registration;i-fill-all-details-for-user-and-do-registration;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 1,
      "name": "@Registration"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "I launch website \"http://newtours.demoaut.com/mercuryreservation.php\"",
  "matchedColumns": [
    0
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "I click on Register link",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "I enter new user details for registration",
  "rows": [
    {
      "cells": [
        "firstName",
        "lastName",
        "phonenumber",
        "email",
        "address1",
        "city",
        "state",
        "postalcode",
        "country",
        "userName",
        "password",
        "conformPassword"
      ],
      "line": 9
    },
    {
      "cells": [
        "akash",
        "kate",
        "1231231231",
        "kateakash@gmail.com",
        "Banglore",
        "Banglore",
        "Banglore",
        "411019",
        "INDIA",
        "kateakash",
        "kateakash",
        "kateakash"
      ],
      "line": 10
    }
  ],
  "keyword": "And "
});
formatter.match({
  "arguments": [
    {
      "val": "http://newtours.demoaut.com/mercuryreservation.php",
      "offset": 18
    }
  ],
  "location": "LoginSteps.launch_website(String)"
});
formatter.result({
  "duration": 1906634371,
  "status": "passed"
});
formatter.match({
  "location": "RegistrationSteps.click_register_link()"
});
formatter.result({
  "duration": 2035984777,
  "status": "passed"
});
formatter.match({
  "location": "RegistrationSteps.fill_registration_details(RegistrationDetails\u003e)"
});
formatter.result({
  "duration": 18787361903,
  "status": "passed"
});
});