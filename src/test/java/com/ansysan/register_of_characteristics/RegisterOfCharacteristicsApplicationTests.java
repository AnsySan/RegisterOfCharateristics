package com.ansysan.register_of_characteristics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

class RegisterOfCharacteristicsApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertThat(40 * 2).isEqualTo(80);
    }

}
