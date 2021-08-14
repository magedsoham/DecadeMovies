# Decade Movies

A simple app to show and view last decade movies.

-When the app is opened for the first time it parses a static JSON file (containing Movies) and saves all the data in a local ROOM database which is then used for
future lookup of movies.

-The app has a Search functionality which enables the user to search among the given Movies list using the Movie Title.
The result of search will display at most 5 Movies for each year sorted by Top Rated Movies (Highest Rated -> Low Rated)

-When any Movie item is clicked, the app navigates the user to another Fragment displaying all details regarding this Movie.
This details fragment also displays a 2-column pictures, fetched from the internet using Flickr API using the Movie Title as the Search Query String.
