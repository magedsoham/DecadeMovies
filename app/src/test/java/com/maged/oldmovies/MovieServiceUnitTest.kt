package com.maged.oldmovies

import com.maged.oldmovies.source.network.MovieService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.net.ssl.HttpsURLConnection

class MovieServiceUnitTest {

    private val mockWebServer = MockWebServer()
    private val SEARCH_RESPONSE_ID = "50362755567"
    private val SIZES_RESPONSE_URL =
        "https://live.staticflickr.com/65535/50357495588_dcced647ca_q.jpg"

    private lateinit var movieService: MovieService

    private val jsonRawSearchResponse =
        "{\n" +
                "    \"photos\": {\n" +
                "        \"page\": 1,\n" +
                "        \"pages\": 249670,\n" +
                "        \"perpage\": 1,\n" +
                "        \"total\": \"249670\",\n" +
                "        \"photo\": [\n" +
                "            {\n" +
                "                \"id\": ${SEARCH_RESPONSE_ID},\n" +
                "                \"owner\": \"7412094@N04\",\n" +
                "                \"secret\": \"eecb22230a\",\n" +
                "                \"server\": \"65535\",\n" +
                "                \"farm\": 66,\n" +
                "                \"title\": \"Looking south on Bamburgh beach\",\n" +
                "                \"ispublic\": 1,\n" +
                "                \"isfriend\": 0,\n" +
                "                \"isfamily\": 0\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"stat\": \"ok\"\n" +
                "}"

    private val jsonRawSizesResponse =
        "{\n" +
                "    \"sizes\": {\n" +
                "        \"canblog\": 0,\n" +
                "        \"canprint\": 0,\n" +
                "        \"candownload\": 0,\n" +
                "        \"size\": [\n" +
                "            {\n" +
                "                \"label\": \"Square\",\n" +
                "                \"width\": 75,\n" +
                "                \"height\": 75,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_s.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/sq/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Large Square\",\n" +
                "                \"width\": 150,\n" +
                "                \"height\": 150,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_q.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/q/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Thumbnail\",\n" +
                "                \"width\": 67,\n" +
                "                \"height\": 100,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_t.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/t/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Small\",\n" +
                "                \"width\": 160,\n" +
                "                \"height\": 240,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_m.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/s/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Small 320\",\n" +
                "                \"width\": 213,\n" +
                "                \"height\": 320,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_n.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/n/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Small 400\",\n" +
                "                \"width\": 267,\n" +
                "                \"height\": 400,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_w.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/w/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Medium\",\n" +
                "                \"width\": 333,\n" +
                "                \"height\": 500,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/m/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Medium 640\",\n" +
                "                \"width\": 427,\n" +
                "                \"height\": 640,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_z.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/z/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Medium 800\",\n" +
                "                \"width\": 533,\n" +
                "                \"height\": 800,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_c.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/c/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Large\",\n" +
                "                \"width\": 683,\n" +
                "                \"height\": 1024,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_dcced647ca_b.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/l/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Large 1600\",\n" +
                "                \"width\": 1067,\n" +
                "                \"height\": 1600,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_f3f8e1d224_h.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/h/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"Large 2048\",\n" +
                "                \"width\": 1366,\n" +
                "                \"height\": 2048,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_d6028184a4_k.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/k/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"X-Large 3K\",\n" +
                "                \"width\": 2048,\n" +
                "                \"height\": 3072,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_1965042236_3k.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/3k/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"label\": \"X-Large 4K\",\n" +
                "                \"width\": 2731,\n" +
                "                \"height\": 4096,\n" +
                "                \"source\": \"https://live.staticflickr.com/65535/50357495588_f373080ea3_4k.jpg\",\n" +
                "                \"url\": \"https://www.flickr.com/photos/99002729@N07/50357495588/sizes/4k/\",\n" +
                "                \"media\": \"photo\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"stat\": \"ok\"\n" +
                "}"

    @Before
    fun setup() {
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        movieService = retrofit.create(MovieService::class.java)
    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchPicsUrl() = runBlocking {

        val searchResponse = MockResponse()
            .setResponseCode(HttpsURLConnection.HTTP_OK)
            .setBody(jsonRawSearchResponse)

        mockWebServer.enqueue(searchResponse)

        val search = movieService.search(text = "")

        Assert.assertEquals(search.photosRoot?.photos?.get(0)?.id, SEARCH_RESPONSE_ID)

        val sizesResponse = MockResponse()
            .setResponseCode(HttpsURLConnection.HTTP_OK)
            .setBody(jsonRawSizesResponse)

        mockWebServer.enqueue(sizesResponse)

        val sizes = movieService.getSizes(photoId = SEARCH_RESPONSE_ID)

        Assert.assertEquals(sizes.photoSizes?.getUrl(), SIZES_RESPONSE_URL)
    }
}