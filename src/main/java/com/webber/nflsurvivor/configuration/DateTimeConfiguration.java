package com.webber.nflsurvivor.configuration;

import com.webber.nflsurvivor.util.DateTimeService;
import com.webber.nflsurvivor.util.DateTimeServiceImpl;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Instant;
import java.util.function.Supplier;

@Configuration
public class DateTimeConfiguration {

    @Bean
    public DateTimeService dateTimeService(Supplier<Instant> timeSupplier) {
        return new DateTimeServiceImpl(timeSupplier);
    }


    @Bean
    @RefreshScope
    public Supplier<Instant> timeSupplier(DateTimeProperties properties) {
        if (properties.isFixed() && properties.getFixedTime() != null
                && !properties.getFixedTime().isEmpty()) {
            Instant mockInstant = Instant.parse(properties.getFixedTime());
            return () -> mockInstant; // Fixed/mock time supplier
        } else {
            return Instant::now; // Real-time supplier
        }
    }
}
