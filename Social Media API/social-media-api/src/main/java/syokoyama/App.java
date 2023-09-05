// AUTHOR: Sebastien Yokoyama
// FILE NAME: App.java
/* FILE DESCRIPTION: Contains the main program
*/

package syokoyama;

import io.javalin.Javalin;

import Controller.SocialMediaController;

public class App 
{
    public static void main( String[] args )
    {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(80);
    }
}
