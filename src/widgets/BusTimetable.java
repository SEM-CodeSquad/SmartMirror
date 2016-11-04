package widgets;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BusTimetable
{
    //TODO please do not delete this one until we fix the new one
    public void setBusTimetable(String busStop, WebView webViewBus)
    {
        WebEngine web = webViewBus.getEngine();
        web.load("http://www.vasttrafik.se/nasta-tur-fullskarm/?externalid=9021014004945000&friendlyname="
                + busStop + "+G%C3%B6teborg");
    }


    //TODO Notes ->

    // TODO GENERATE TOKEN:

    // POST https://api.vasttrafik.se/token HTTP/1.1
    // Content-Type: application/x-www-form-urlencoded
    // Authorization: Basic UmJseEkyeTFsWVNFTTZ0Z2J6anBTa2E0R1o6Wk1nSkR0Y0paRGV4OTJldUxpQUdYOFExUnU=
    // grant_type=client_credentials&scope=<device_id>   --------(device_id needs to be specified)-------

    // TODO Example link that returns a json object containing departures:
    // NOTE: Needs authentication token, done through Oauth2

    // https://api.vasttrafik.se/bin/rest.exe/v2/departureBoard?id=Brunnsparken%2CG%C3%B6teborg&date=2016-11-04&time=19%3A22&format=json

    // TODO Fetch the json object with the auth code:

    // GET https://api.vasttrafik.se/bin/rest.exe/v2/location.name?input=ols&format=json HTTP/1.1
    // Authorization: Bearer 766b697f4462ca3878b05597ff19313

    // TODO Here's a link to git where some dude made it work:
    // https://github.com/vasttrafik/wso2-apim-developer-portal-api/tree/master/java/src
}
