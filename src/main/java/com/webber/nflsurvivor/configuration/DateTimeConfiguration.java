package com.webber.nflsurvivor.configuration;

import com.webber.nflsurvivor.util.DateTimeService;
import com.webber.nflsurvivor.util.DateTimeServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Instant;
import java.util.function.Supplier;

@Configuration
public class DateTimeConfiguration {

    @Value("${datetime.fixed:false}")
    private boolean useFixedTime;

    @Value("${datetime.fixed-time:}")
    private String fixedTime;

    @Bean
    public DateTimeService dateTimeService(Supplier<Instant> timeSupplier) {
        return new DateTimeServiceImpl(timeSupplier);
    }


    @Bean
    public Supplier<Instant> timeSupplier() {
        if (useFixedTime && !fixedTime.isEmpty()) {
            Instant mockInstant = Instant.parse(fixedTime);
            return () -> mockInstant; // Fixed/mock time supplier
        } else {
            return Instant::now; // Real-time supplier
        }
    }
}
