# avaloq-liferay-project

 @Author: Vaibhav Khopade 

Note: All Avaloq test Assignments are developed using Liferay DXP 7.2 (CE).

# Assignment 1:

'AvaloqRestPortlet' Module: 

This rest portlet liferay osgi module is developed to receive http rest calls which will return page hierarchy of configured Site. This rest call returns with JSON formatted page properties like name,title,id,custom field and list of child pages (sub pages)with their details.


![screenshot shows the JSON output with site page hierarchy](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/rest-call-result-page-heirachy.png)



Steps for Deployment of 'AvaloqRestPortlet' Module:

1) Create a Site from control panel 

2) Copy generated Site Id and Modify 'ConstantsKeys.java' class .Replace value of SITE_GROUP_ID with your generated long group id.

e.g. public static final long SITE_GROUP_ID = 34328l;

![Screenshot of Site Setting:](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/Site-settings.png)

3) Create Custom field on Page with key ' custom-additional-info'. 

![Screenshot of custom-additional-info:](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/custom-field.png)
 

4) Deploy portlet in osgi on Liferay dxp 7.2. 

![Screenshot of GoGo Shell successfully deployment :](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/gogo-shell.png)
 


5) Create 'AvaloqRestApplication'  from control panel/configuration/ OAuth 2 Administration.

![Screenshot Of OAuth 2  Application Configuration:](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/OAuth-Configuration-control-panel.png)


-------------------------------------------------------------------------------------------

# Assignment 2:

Assumption Made: 

It is assumed that this rest point will be accessed by applications outside Liferay DXP. So no logged in user authorization is taken under consideration for the time being.

If logged in user authorization is requirement then it can be easily implement by adding Authorization Filters to this end point which can check if user is logged in and user has proper roles to access this end point. Further deeper permission checks filters can be applied on access rights for pages.



# A)End Point Security Permissions Concerns: 

This end point is protected by OAUTH 2.0 authorization. 
So OAuth 2.0 application is created to provide access to rest service. For testing purpose 'Headless Server profile' authorization type is used. There are many other options available for production as per OAuth2.0 specifications.

Following screenshot shows the configuration of 'AvaloqRestApplication' where client-id and client-secret is generated for authorization.
  
Getting access:

1) Client can get access token by curl with client-id and client-secret.

curl http://localhost:8080/o/oauth2/token -d 'grant_type=client_credentials&client_id=id-cdd8daf0-5edf-6f7a-5932-957e78d51a5f&client_secret=secret-5053cd5c-e79b-74f4-2ac7-316cf18b862'

![Screenshot Of Authentication Token by Curl:](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/Auth-2-authentication-token.png)

2) Make a call to end point using generated access_token . 
e.g. access_token: "ee63aac988c144b56a71ef8c866c54f959d4ddddd86fbd81c279e2aed8fb281"
Making call to end ' http://localhost:8080/o/avaloq/sitepages':
curl --header "Authorization: Bearer ee63aac988c144b56a71ef8c866c54f959d4ddddd86fbd81c279e2aed8fb281" http://localhost:8080/o/avaloq/sitepages


![Screenshot shows the JSON output with site page hierarchy](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/rest-call-result-page-heirachy.png)


![Screenshot Of Site Page Level:](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/page-levels.png)

-------------------------------------------------------------------------------------------------------------

# B) OSGi Significance In 'AvaloqRestPortlet':

'AvaloqRestPortlet' Module is created and deployed as liferay OSGi module.

Liferay OSGi framework takes cares of all modules dependencies and they are automatically managed by container. So this module can be installed,started,updated,stopped and uninstall without stopping running Liferay Portal. OSGi will hide all module classes. OSGi can handle different version of same module.Also Liferay service classes(Interfaces) are available to our module since we declare them in gradle file as dependency modules.


e.g. OSGi Configuration file:  bnd.bnd 

Bundle-Name: AvaloqRestPortlet

Bundle-SymbolicName: com.avaloq.rest

Bundle-Version: 1.0.0

Liferay-Configuration-Path: /configuration

Export-Package:com.avaloq.rest.service.interfaces

'com.avaloq.rest.service.interfaces' is exported and made available to other modules as provider. So other modules if they requires PageInformation service then they just need to declare Interface as @reference in their classes. Actual implementation is handle by 'AvaloqRestPortlet' module . This way it is loosely coupled and managed easily.

e.g. PageInformationService.java implements PageInformationInteface.java in exported package   which has Annotation @Component and immediate=true means module is not lazy-loaded and service registry loads it as soon as its deployed.


-------------------------------------------------------------------------------------------

# Assignment 3:

'UserInformationPortlet': 

This portlet module is developed to show user information like roles,userid,username,email and web content titles which user has access.
Portlet is created as Liferay MVC portlet.

Different site members can be created with different roles . Each web content need to set permissions on  user group .

Following are screenshots of different users with different roles like 'Site Members','Site owner' ,Administrator etc.
 

![1) Screenshot Of Test Test with Site Admin Role:](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/test-test.png)


2) Lalita Markopoulu with site content reviewer role and portal content reviewer. 

![ 2) Screenshot Of Another user with site content reviewer role and portal content reviewer](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/lalita.png)



![3) Screenshot Of Another user with site content reviewer role :](https://github.com/lalitavai-vaibhav/avaloq-liferay-project/tree/master/screenshots/anandi.png)

