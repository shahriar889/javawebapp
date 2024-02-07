package com.julfiker.admin.utils;

import org.springframework.stereotype.Component;

@Component
public class RatingUtils {
    public String generateStarIcons(double rating) {
        int fullStars = (int) Math.floor(rating);
        double remainder = rating - fullStars;
        StringBuilder stars = new StringBuilder();

        // Add full stars
        stars.append("<i class=\"fa fa-star\"></i>".repeat(Math.max(0, fullStars)));

        // Add half star if remainder is >= 0.5
        if (remainder >= 0.5) {
            stars.append("<i class=\"fa fa-star-half\"></i>");
        }

        // Add crescent for remainder < 0.5
        if (remainder > 0 &&remainder < 0.5) {
            stars.append("<i class=\"fa fa-star-and-crescent\"></i>");
        }

        // Add empty stars to complete 5 stars
        int emptyStars = 5 - fullStars - (remainder >= 0.5 ? 1 : 0);
        stars.append("<i class=\"fa fa-star-o\"></i>".repeat(Math.max(0, emptyStars)));

        return stars.toString();
    }
}
