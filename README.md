Simple app getting images from the Flickr API by current coordinate.
Saves Image every 100m up until the Stop button is pressed.
To Start the service press the Start button on the top right.

Learnings while developing:
I started first with learning about the flickr API, to get images with coordinates was pretty straightforward.
I soon realized that there aren't that many location based images around Amsterdam, so decided that app will pick a random picture from the top 50 closest around.
As in the challenge stated, the app needs to call this API every 100m, so I looked up how to use a location based service, as it was quite a long time ago since I needed periodic location updates in the background.
The Android documentation advised to use a foreground service, so I went with that.
Adding all the permissions weren't straightforward, but I went without using an external library, instead I'm using how the official documentation recommends.
I realized that the permanently decline method isn't called on my phone, but the warnings are everywhere in the app, so I decided that's a user error if he keeps on declining location permissions.
So the app was ready to receive a photo every 100 meters, I decided to use Room DB to save the data from the backend.
The UI is written in Compose in which I feel more and more confident with.
The UI simply connects to the database as a single source of truth, and shows the images by the data received in the foreground service.
I realized that when the app in the background tries to connect to the Internet, there can be cases without internet connection.
I created a different table for errors while trying to get the data from the Flickr API, and I save these errors with coordinates where they happened.
This enables a retry mechanism and also troubleshooting if necessary. I didn't add these as it started to feel like a bit over engineering as in the original challenge it doesn't state what to do with failed location attempts.
I also learned while testing the app; I haven't used my device's developer options -> mock location function, which works pretty nicely as I figured out.
After writing Unit Tests I wrapped up the project, and sent it.
