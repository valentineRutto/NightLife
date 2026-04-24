package com.valentinerutto.nightlife.data.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    const val BASE_URL = "https://fake.api/"

    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {

        "application/json".toMediaType()
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun createOkClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(createLoggingInterceptor()).addInterceptor(FakeInterceptor())
            .build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY

        }
    }


    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }




}
class FakeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val fakeJson = """
[
  {
    "id": "1",
    "title": "Afrobeats Night",
    "description": "Best vibes in Nairobi",
    "imageUrl": "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=800",
    "location": "Westlands",
    "dateTime": 1745481600000,
    "price": 1000.0,
    "isSoldOut": false,
    "category": "Music"
  },
  {
    "id": "2",
    "title": "Jazz & Wine Evening",
    "description": "Smooth jazz with a curated wine selection",
    "imageUrl": "https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?w=800",
    "location": "Karen",
    "dateTime": 1745568000000,
    "price": 1500.0,
    "isSoldOut": true,
    "category": "Music"
  },
  {
    "id": "3",
    "title": "Nairobi Food Festival",
    "description": "A celebration of local and international cuisines",
    "imageUrl": "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=800",
    "location": "Uhuru Gardens",
    "dateTime": 1745654400000,
    "price": 500.0,
    "isSoldOut": false,
    "category": "Food & Drink"
  },
  {
    "id": "4",
    "title": "Tech Startup Summit",
    "description": "Connect with innovators and investors shaping Africa's future",
    "imageUrl": "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800",
    "location": "iHub, Ngong Road",
    "dateTime": 1745740800000,
    "price": 2500.0,
    "isSoldOut": true,
    "category": "Technology"
  },
  {
    "id": "5",
    "title": "Open Mic Comedy Night",
    "description": "Laugh out loud with Nairobi's funniest comedians",
    "imageUrl": "https://images.unsplash.com/photo-1527224538127-2104bb71c51b?w=800",
    "location": "The Alchemist, Westlands",
    "dateTime": 1745827200000,
    "price": 800.0,
    "isSoldOut": false,
    "category": "Comedy"
  },
  {
    "id": "6",
    "title": "Reggae Sundowner",
    "description": "Chill reggae vibes as the sun sets over the city",
    "imageUrl": "https://images.unsplash.com/photo-1506157786151-b8491531f063?w=800",
    "location": "Karura Forest",
    "dateTime": 1745913600000,
    "price": 600.0,
    "isSoldOut": false,
    "category": "Music"
  },
  {
    "id": "7",
    "title": "Yoga & Wellness Retreat",
    "description": "A full-day retreat for mind, body and soul",
    "imageUrl": "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=800",
    "location": "Ngong Hills",
    "dateTime": 1746000000000,
    "price": 3000.0,
    "isSoldOut": true,
    "category": "Health & Wellness"
  },
  {
    "id": "8",
    "title": "Nairobi Marathon 2026",
    "description": "Run through the heart of Nairobi in this annual race",
    "imageUrl": "https://images.unsplash.com/photo-1530549387789-4c1017266635?w=800",
    "location": "Nyayo Stadium",
    "dateTime": 1746086400000,
    "price": 1200.0,
    "isSoldOut": false,
    "category": "Sports"
  },
  {
    "id": "9",
    "title": "Art Gallery Opening",
    "description": "Discover emerging East African artists at this exclusive opening",
    "imageUrl": "https://images.unsplash.com/photo-1531058020387-3be344556be6?w=800",
    "location": "Rasanga Art Space, Kilimani",
    "dateTime": 1746172800000,
    "price": 0.0,
    "isSoldOut": false,
    "category": "Arts & Culture"
  },
  {
    "id": "10",
    "title": "Gospel Concert Live",
    "description": "An uplifting evening of praise and worship",
    "imageUrl": "https://images.unsplash.com/photo-1516280440614-37939bbacd81?w=800",
    "location": "KICC Grounds",
    "dateTime": 1746259200000,
    "price": 700.0,
    "isSoldOut": true,
    "category": "Music"
  },
  {
    "id": "11",
    "title": "Fashion Week Nairobi",
    "description": "Showcasing top African designers on the runway",
    "imageUrl": "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800",
    "location": "Sarit Centre",
    "dateTime": 1746345600000,
    "price": 2000.0,
    "isSoldOut": false,
    "category": "Fashion"
  },
  {
    "id": "12",
    "title": "Night Market Bazaar",
    "description": "Shop local crafts, street food and live performances",
    "imageUrl": "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800",
    "location": "Two Rivers Mall",
    "dateTime": 1746432000000,
    "price": 0.0,
    "isSoldOut": false,
    "category": "Food & Drink"
  },
  {
    "id": "13",
    "title": "Hip Hop Cypher",
    "description": "Underground hip hop battles and freestyle sessions",
    "imageUrl": "https://images.unsplash.com/photo-1547592180-85f173990554?w=800",
    "location": "Blankets & Wine Grounds",
    "dateTime": 1746518400000,
    "price": 900.0,
    "isSoldOut": true,
    "category": "Music"
  },
  {
    "id": "14",
    "title": "Photography Masterclass",
    "description": "Learn pro photography skills from award-winning photographers",
    "imageUrl": "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800",
    "location": "GoDown Arts Centre",
    "dateTime": 1746604800000,
    "price": 4000.0,
    "isSoldOut": true,
    "category": "Education"
  },
  {
    "id": "15",
    "title": "Kids Fun Day",
    "description": "A day full of games, bouncy castles and entertainment for kids",
    "imageUrl": "https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?w=800",
    "location": "City Park, Parklands",
    "dateTime": 1746691200000,
    "price": 400.0,
    "isSoldOut": false,
    "category": "Family"
  },
  {
    "id": "16",
    "title": "Electronic Music Festival",
    "description": "Top DJs spinning deep house and techno all night",
    "imageUrl": "https://images.unsplash.com/photo-1571266028243-e4733b0f0bb0?w=800",
    "location": "Club Privee, Westlands",
    "dateTime": 1746777600000,
    "price": 1800.0,
    "isSoldOut": false,
    "category": "Music"
  },
  {
    "id": "17",
    "title": "Business Networking Brunch",
    "description": "Grow your professional network over a delicious brunch",
    "imageUrl": "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800",
    "location": "Radisson Blu, Upper Hill",
    "dateTime": 1746864000000,
    "price": 3500.0,
    "isSoldOut": false,
    "category": "Business"
  },
  {
    "id": "18",
    "title": "Spoken Word Poetry Night",
    "description": "Raw, powerful stories told through the art of spoken word",
    "imageUrl": "https://images.unsplash.com/photo-1474932430478-367dbb6832c1?w=800",
    "location": "Pawa254, Kilimani",
    "dateTime": 1746950400000,
    "price": 500.0,
    "isSoldOut": false,
    "category": "Arts & Culture"
  },
  {
    "id": "19",
    "title": "Rooftop Cinema",
    "description": "Watch classic films under the stars with the city skyline as your backdrop",
    "imageUrl": "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=800",
    "location": "14 Riverside Drive",
    "dateTime": 1747036800000,
    "price": 1100.0,
    "isSoldOut": true,
    "category": "Film"
  },
  {
    "id": "20",
    "title": "Cultural Heritage Day",
    "description": "Celebrate Kenya's rich cultural diversity with food, dance and exhibitions",
    "imageUrl": "https://images.unsplash.com/photo-1504450758481-7338eba7524a?w=800",
    "location": "National Museum, Museum Hill",
    "dateTime": 1747123200000,
    "price": 300.0,
    "isSoldOut": false,
    "category": "Arts & Culture"
  },
  {
    "id": "21",
    "title": "Salsa & Latin Dance Night",
    "description": "Dance the night away with salsa, bachata and merengue",
    "imageUrl": "https://images.unsplash.com/photo-1504609813442-a8924e83f76e?w=800",
    "location": "Havana Bar, Westlands",
    "dateTime": 1747209600000,
    "price": 1000.0,
    "isSoldOut": false,
    "category": "Dance"
  }

]
        """.trimIndent()

        return Response.Builder()
            .code(200)
            .message(fakeJson)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(
                fakeJson.toByteArray().toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}