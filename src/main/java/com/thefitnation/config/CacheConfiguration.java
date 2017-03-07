package com.thefitnation.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.thefitnation.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserDemographic.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserDemographic.class.getName() + ".gyms", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserDemographic.class.getName() + ".userWeights", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserDemographic.class.getName() + ".workoutTemplates", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserDemographic.class.getName() + ".userWorkoutTemplates", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Gym.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Gym.class.getName() + ".userDemographics", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserWeight.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.WorkoutTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.WorkoutTemplate.class.getName() + ".workoutInstances", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.WorkoutTemplate.class.getName() + ".userWorkoutTemplates", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.SkillLevel.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.WorkoutInstance.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.WorkoutInstance.class.getName() + ".userWorkoutInstances", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.WorkoutInstance.class.getName() + ".exerciseInstances", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.ExerciseInstance.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.ExerciseInstance.class.getName() + ".userExerciseInstances", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.ExerciseInstance.class.getName() + ".exerciseInstanceSets", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.ExerciseInstanceSet.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.ExerciseInstanceSet.class.getName() + ".userExerciseInstanceSets", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Unit.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Exercise.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Exercise.class.getName() + ".exerciseInstances", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Exercise.class.getName() + ".muscles", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Muscle.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Muscle.class.getName() + ".exercises", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserWorkoutTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserWorkoutTemplate.class.getName() + ".userWorkoutInstances", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserWorkoutInstance.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserWorkoutInstance.class.getName() + ".userExerciseInstances", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserExerciseInstance.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserExerciseInstance.class.getName() + ".userExerciseInstanceSets", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.UserExerciseInstanceSet.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.BodyPart.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.ExerciseFamily.class.getName(), jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.ExerciseFamily.class.getName() + ".exercises", jcacheConfiguration);
            cm.createCache(com.thefitnation.domain.BodyPart.class.getName() + ".muscles", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
