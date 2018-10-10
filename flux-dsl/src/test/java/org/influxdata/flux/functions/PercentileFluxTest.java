/*
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.influxdata.flux.functions;

import java.util.Arrays;

import org.influxdata.flux.Flux;
import org.influxdata.flux.functions.PercentileFlux.MethodType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * @author Jakub Bednar (10/10/2018 12:38)
 */
@RunWith(JUnitPlatform.class)
class PercentileFluxTest {

    @Test
    void percentile() {

        Flux flux = Flux
                .from("telegraf")
                .percentile()
                    .withColumns(new String[]{"value2"})
                    .withPercentile(0.75F)
                    .withMethod(MethodType.EXACT_MEAN)
                    .withCompression(2_000F);

        String expected = "from(bucket:\"telegraf\") |> "
                + "percentile(columns:[\"value2\"], percentile:0.75, method:\"exact_mean\", compression:2000.0)";
        Assertions.assertThat(flux.toString()).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    void percentilePercentile() {

        Flux flux = Flux
                .from("telegraf")
                .percentile(0.80F);

        Assertions.assertThat(flux.toString()).isEqualToIgnoringWhitespace("from(bucket:\"telegraf\") |> percentile(percentile:0.8)");
    }

    @Test
    void percentilePercentileMethod() {

        Flux flux = Flux
                .from("telegraf")
                .percentile(0.80F, MethodType.EXACT_SELECTOR);

        Assertions.assertThat(flux.toString())
                .isEqualToIgnoringWhitespace("from(bucket:\"telegraf\") |> percentile(percentile:0.8, method:\"exact_selector\")");
    }

    @Test
    void percentilePercentileMethodCompression() {

        Flux flux = Flux
                .from("telegraf")
                .percentile(0.80F, MethodType.EXACT_SELECTOR, 3_000F);

        Assertions.assertThat(flux.toString()).isEqualToIgnoringWhitespace("from(bucket:\"telegraf\") |> percentile(percentile:0.8, method:\"exact_selector\", compression:3000.0)");
    }

    @Test
    void percentileColumnsPercentileMethodCompression() {

        Flux flux = Flux
                .from("telegraf")
                .percentile(new String[]{"_value", "_value2"}, 0.80F, MethodType.EXACT_SELECTOR, 3_000F);

        String expected = "from(bucket:\"telegraf\") |> "
                + "percentile(columns:[\"_value\", \"_value2\"], percentile:0.8, method:\"exact_selector\", compression:3000.0)";
        Assertions.assertThat(flux.toString()).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    void percentileColumnsListPercentileMethodCompression() {

        String[] columns = {"_value", "_value2"};

        Flux flux = Flux
                .from("telegraf")
                .percentile(Arrays.asList(columns), 0.80F, MethodType.ESTIMATE_TDIGEST, 3_000F);

        String expected = "from(bucket:\"telegraf\") |> "
                + "percentile(columns:[\"_value\", \"_value2\"], percentile:0.8, method:\"estimate_tdigest\", compression:3000.0)";
        
        Assertions.assertThat(flux.toString()).isEqualToIgnoringWhitespace(expected);
    }
}