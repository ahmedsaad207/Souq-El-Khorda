package com.delighted2wins.souqelkhorda.features.buyers.data

import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto

val sampleBuyers = listOf(
    BuyerDto(
        buyerID = "1",
        name = "محل أبو أحمد للخردة",
        email = "aboahmed@example.com",
        phone = "01012345678",
        governorate = "القاهرة",
        address = "شارع الجمهورية، وسط البلد",
        area = "وسط البلد",
        imageUrl = null,
        longitude = 31.2357,
        latitude = 30.0444,
        types = listOf("حديد", "ألومنيوم", "بلاستيك")
    ),
    BuyerDto(
        buyerID = "2",
        name = "Al-Masry Scrap Shop",
        email = "almasry@example.com",
        phone = "+201011223344",
        governorate = "Giza",
        address = "26th of July Street, Mohandessin",
        area = "Mohandessin",
        imageUrl = null,
        longitude = 31.2035,
        latitude = 30.0500,
        types = listOf("Paper", "Plastic", "Copper")
    ),
    BuyerDto(
        buyerID = "3",
        name = "محل أولاد الحاج حسن",
        email = "hasansons@example.com",
        phone = "01099887766",
        governorate = "الإسكندرية",
        address = "شارع فؤاد، محطة الرمل",
        area = "محطة الرمل",
        imageUrl = null,
        longitude = 29.9187,
        latitude = 31.2001,
        types = listOf("نحاس", "بلاستيك", "ورق")
    ),
    BuyerDto(
        buyerID = "4",
        name = "Green Recycling Center",
        email = "greenrecycle@example.com",
        phone = "+201055566677",
        governorate = "Cairo",
        address = "Nasr City, Makram Ebeid Street",
        area = "Nasr City",
        imageUrl = null,
        longitude = 31.3456,
        latitude = 30.0654,
        types = listOf("Aluminum", "Glass", "Steel")
    ),
    BuyerDto(
        buyerID = "5",
        name = "محل أبو كريم",
        email = "abokarim@example.com",
        phone = "01122334455",
        governorate = "أسيوط",
        address = "حي غرب، ميدان المحطة",
        area = "حي غرب",
        imageUrl = null,
        longitude = 31.1837,
        latitude = 27.1801,
        types = listOf("حديد", "ألومنيوم")
    ),
    BuyerDto(
        buyerID = "6",
        name = "Future Scrap Buyers",
        email = "futurebuyers@example.com",
        phone = "+201077889900",
        governorate = "Alexandria",
        address = "Sidi Gaber, Mostafa Kamel Street",
        area = "Sidi Gaber",
        imageUrl = null,
        longitude = 29.9500,
        latitude = 31.2200,
        types = listOf("Paper", "Plastic", "Electronics")
    )
)
