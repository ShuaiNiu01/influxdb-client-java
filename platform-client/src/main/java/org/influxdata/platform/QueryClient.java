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
package org.influxdata.platform;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.influxdata.flux.domain.FluxRecord;
import org.influxdata.flux.domain.FluxTable;
import org.influxdata.platform.rest.Cancellable;

/**
 * The client of the InfluxData Platform that implement Query HTTP API endpoint.
 *
 * @author Jakub Bednar (bednar@github) (01/10/2018 12:17)
 */
public interface QueryClient {

    /**
     * Execute a Flux query against the InfluxDB and synchronously map whole response to {@link FluxTable}s.
     *
     * @param query the flux query to execute
     * @return {@code List<FluxTable>} which are matched the query
     */
    @Nonnull
    List<FluxTable> query(@Nonnull final String query);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream {@link FluxRecord}s
     * to {@code onNext} consumer.
     *
     * @param query  the flux query to execute
     * @param onNext callback to consume result which are matched the query
     *               with capability to discontinue a streaming query
     */
    void query(@Nonnull final String query,
               @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream {@link FluxRecord}s
     * to {@code onNext} consumer.
     *
     * @param query   the flux query to execute
     * @param onNext  callback to consume result which are matched the query
     *                with capability to discontinue a streaming query
     * @param onError callback to consume any error notification
     */
    void query(@Nonnull final String query,
               @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext,
               @Nonnull final Consumer<? super Throwable> onError);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream {@link FluxRecord}s
     * to {@code onNext} consumer.
     *
     * @param query      the flux query to execute
     * @param onNext     callback to consume result which are matched the query
     *                   with capability to discontinue a streaming query
     * @param onError    callback to consume any error notification
     * @param onComplete callback to consume a notification about successfully end of stream
     */
    void query(@Nonnull final String query,
               @Nonnull final BiConsumer<Cancellable, FluxRecord> onNext,
               @Nonnull final Consumer<? super Throwable> onError,
               @Nonnull final Runnable onComplete);

    /**
     * Execute a Flux query against the InfluxDB and synchronously map whole response to {@link String} result.
     *
     * @param query the flux query to execute
     * @return the raw response that matched the query
     */
    @Nonnull
    String raw(@Nonnull final String query);

    /**
     * Execute a Flux query against the InfluxDB and synchronously map whole response to {@link String} result.
     *
     * @param query   the flux query to execute
     * @param dialect Dialect is an object defining the options to use when encoding the response.
     *                <a href="http://bit.ly/flux-dialect">See dialect SPEC.</a>.
     * @return the raw response that matched the query
     */
    @Nonnull
    String raw(@Nonnull final String query, @Nullable final String dialect);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream response
     * (line by line) to {@code onResponse}.
     *
     * @param query      the flux query to execute
     * @param onResponse callback to consume the raw response which are matched the query.
     *                   The callback call contains the one line of the response.
     */
    void raw(@Nonnull final String query,
             @Nonnull final BiConsumer<Cancellable, String> onResponse);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream response
     * (line by line) to {@code onResponse}.
     *
     * @param query      the flux query to execute
     * @param dialect    Dialect is an object defining the options to use when encoding the response.
     *                   <a href="http://bit.ly/flux-dialect">See dialect SPEC.</a>.
     * @param onResponse callback to consume the raw response which are matched the query.
     *                   The callback call contains the one line of the response.
     */
    void raw(@Nonnull final String query,
             @Nullable final String dialect,
             @Nonnull final BiConsumer<Cancellable, String> onResponse);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream response
     * (line by line) to {@code onResponse}.
     *
     * @param query      the flux query to execute
     * @param onResponse callback to consume the raw response which are matched the query.
     *                   The callback call contains the one line of the response.
     * @param onError    callback to consume any error notification
     */
    void raw(@Nonnull final String query,
             @Nonnull final BiConsumer<Cancellable, String> onResponse,
             @Nonnull final Consumer<? super Throwable> onError);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream response
     * (line by line) to {@code onResponse}.
     *
     * @param query      the flux query to execute
     * @param dialect    Dialect is an object defining the options to use when encoding the response.
     *                   <a href="http://bit.ly/flux-dialect">See dialect SPEC.</a>.
     * @param onResponse callback to consume the raw response which are matched the query.
     *                   The callback call contains the one line of the response.
     * @param onError    callback to consume any error notification
     */
    void raw(@Nonnull final String query,
             @Nullable final String dialect,
             @Nonnull final BiConsumer<Cancellable, String> onResponse,
             @Nonnull final Consumer<? super Throwable> onError);

    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream response
     * (line by line) to {@code onResponse}.
     *
     * @param query      the flux query to execute
     * @param onResponse callback to consume the raw response which are matched the query.
     *                   The callback call contains the one line of the response.
     * @param onError    callback to consume any error notification
     * @param onComplete callback to consume a notification about successfully end of stream
     */
    void raw(@Nonnull final String query,
             @Nonnull final BiConsumer<Cancellable, String> onResponse,
             @Nonnull final Consumer<? super Throwable> onError,
             @Nonnull final Runnable onComplete);


    /**
     * Execute a Flux query against the InfluxDB and asynchronously stream response
     * (line by line) to {@code onResponse}.
     *
     * @param query      the flux query to execute
     * @param dialect    Dialect is an object defining the options to use when encoding the response.
     *                   <a href="http://bit.ly/flux-dialect">See dialect SPEC.</a>.
     * @param onResponse callback to consume the raw response which are matched the query.
     *                   The callback call contains the one line of the response.
     * @param onError    callback to consume any error notification
     * @param onComplete callback to consume a notification about successfully end of stream
     */
    void raw(@Nonnull final String query,
             @Nullable final String dialect,
             @Nonnull final BiConsumer<Cancellable, String> onResponse,
             @Nonnull final Consumer<? super Throwable> onError,
             @Nonnull final Runnable onComplete);
}