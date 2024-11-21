<strong> **DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. ** </strong>

# WESTERN GOVERNORS UNIVERSITY 
## D387 – ADVANCED JAVA

A.  Create your subgroup and project in GitLab using the provided web link and the "GitLab How-To" web link by doing the following:

•   Clone the project to the IDE.

•   Commit with a message and push when you complete each requirement listed in parts B1, B2, B3, and C1.


Note: You may commit and push whenever you want to back up your changes, even if a requirement is not yet complete.


•   Submit a copy of the GitLab repository URL in the "Comments to Evaluator" section when you submit this assessment.

•   Submit a copy of the repository branch history retrieved from your repository, which must include the commit messages and dates.

Note: Wait until you have completed all the following prompts before you create your copy of the repository branch history.


B.  Modify the Landon Hotel scheduling application for localization and internationalization by doing the following:

1.   Install the Landon Hotel scheduling application in your integrated development environment (IDE). Modify the Java classes of application to display a welcome message by doing the following:

a.  Build resource bundles for both English and French (languages required by Canadian law). Include a welcome message in the language resource bundles.

-Created Resource Bundle 'translation' and added the following code to translation_en_US.properties:

hello=Hello!
welcome=Welcome to the Landon Hotel!

and the following code to translation_fr_CA.properties:

hello=Bonjour!
welcome=Bienvenue à l'hôtel Landon

b.  Display the welcome message in both English and French by applying the resource bundles using a different thread for each language.


Note: You may use Google Translate for the wording of your welcome message.

-Created Package 'Translations'.

-Created WelcomeMessage.java and added the following code:

package edu.wgu.d387_sample_code.Translations;

import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeMessage implements Runnable {

Locale locale;

public WelcomeMessage(Locale locale){
this.locale = locale;
}

public String getWelcomeMessage(){
ResourceBundle bundle = ResourceBundle.getBundle("translation", locale);
return bundle.getString("welcome");
}
@Override
public void run() {
System.out.println("Thread verification: " + getWelcomeMessage() + ", ThreadID: " + Thread.currentThread().getId());
}
}

-Created WelcomeController.java and added the following code:

package edu.wgu.d387_sample_code.Translations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WelcomeMessageController {
@GetMapping("/welcome")
public ResponseEntity<String> displayWelcome(@RequestParam("lang") String lang) {
Locale locale = Locale.forLanguageTag(lang);
WelcomeMessage welcomeMessage = new WelcomeMessage(locale);
return new ResponseEntity<String> (welcomeMessage.getWelcomeMessage(), HttpStatus.OK);
}
}

-Modified D387SampleCodeApplication.java, lines 14-21 with the following code:

WelcomeMessage welcomeMessageEnglish = new WelcomeMessage(Locale.US);
Thread englishWelcomeThread = new Thread(welcomeMessageEnglish);
englishWelcomeThread.start();

WelcomeMessage welcomeMessageFrench = new WelcomeMessage(Locale.CANADA_FRENCH);
Thread frenchWelcomeThread = new Thread(welcomeMessageFrench);
frenchWelcomeThread.start();

-Modified app.component.html, Lines 17-20 added the following code:

<div>
<h1>{{welcomeMessageEnglish$ | async}}</h1>
<h1>{{welcomeMessageFrench$ | async}}</h1>
</div>

-Modified app.component.ts, Lines 18-19 and lines 35-36 with the following code:

welcomeMessageEnglish$!: Observable<string>
welcomeMessageFrench$!: Observable<string>

this.welcomeMessageFrench$ = this.httpClient.get(this.baseURL + '/welcome?lang=fr-CA', {responseType: 'text'})
this.welcomeMessageEnglish$ = this.httpClient.get(this.baseURL + '/welcome?lang=en-US', {responseType: 'text'})

2.  Modify the front end to display the price for a reservation in currency rates for U.S. dollars ($), Canadian dollars (C$), and euros (€) on different lines.


Note: It is not necessary to convert the values of the prices.

-Modified app.component.ts Line 59 with the following code:

this.rooms.forEach(room => {room.priceCAD = room.price; room.priceEUR = room.price})

-Modified app.component.ts Lines 110-111 with the following code:

priceCAD:string;
priceEUR:string;

-Modified app.component.html Lines 83-84 added the following code:

<strong>Price: CA${{room.priceCAD}}</strong><br>
<strong>Price: EUR€{{room.priceEUR}}</strong><br>

3.  Display the time for an online live presentation held at the Landon Hotel by doing the following:

a.  Write a Java method to convert times between eastern time (ET), mountain time (MT), and coordinated universal time (UTC) zones.

-Created package TimeZones.

-Created TimeZones.java, added the following code:

package edu.wgu.d387_sample_code.TimeZones;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:4200")
public class TimeZones {

public static String getTime() {
ZonedDateTime time = ZonedDateTime.now();
DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

ZonedDateTime est = time.withZoneSameInstant(ZoneId.of("America/New_York"));
ZonedDateTime mst = time.withZoneSameInstant(ZoneId.of("America/Denver"));
ZonedDateTime utc = time.withZoneSameInstant(ZoneId.of("UTC"));

String times = est.format(timeFormat) + "EST, " + mst.format(timeFormat) + "MST, " + utc.format(timeFormat) + "UTC";
return times;
}
}


-Created TimeZonesController.java and added the following code:

package edu.wgu.d387_sample_code.TimeZones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimeZonesController {
@GetMapping("/presentation")
public ResponseEntity<String> announcePresentation(){
String announcement = "The presentation will begin at: " + TimeZones.getTime();
return new ResponseEntity<String> (announcement, HttpStatus.OK);
}
}



b.  Use the time zone conversion method from part B3a to display a message stating the time in all three times zones in hours and minutes for an online, live presentation held at the Landon Hotel. The times should be displayed as ET, MT, and UTC.


-Modified app.component.ts Line 21 with the following code:

announcePresentation$!: Observable<string>

-Modified app.component.ts Line 40 with the following code:

this.announcePresentation$ = this.httpClient.get(this.baseURL + '/presentation', {responseType: 'text'})

-Modified app.component.html Lines 30-32 added the following code:

<div class="scene" id="presentation">
<h1>{{announcePresentation$ | async}}</h1>
</div><br><br>

C.  Explain how you would deploy the Spring application with a Java back end and an Angular front end to cloud services and create a Dockerfile using the attached supporting document "How to Create a Docker Account" by doing the following:

1.  Build the Dockerfile to create a single image that includes all code, including modifications made in parts B1 to B3. Commit and push the final Dockerfile to GitLab.

2.  Test the Dockerfile by doing the following:

•   Create a Docker image of the current multithreaded Spring application.

•   Run the Docker image in a container and give the container a name that includes D387_[student ID].

•   Submit a screenshot capture of the running application with evidence it is running in the container.

3.  Describe how you would deploy the current multithreaded Spring application to the cloud. Include the name of the cloud service provider you would use.


Note: Remember to commit and push your changes to GitLab.


D.  Demonstrate professional communication in the content and presentation of your submission.