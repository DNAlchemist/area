import org.akhikhl.gretty.GrettyAjaxSpec

import java.util.concurrent.TimeUnit

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import static javax.servlet.http.HttpServletResponse.*

class ServletIT extends GrettyAjaxSpec {

    def 'test add person'() {
        when:
        def result = [:]
        conn.request( GET, TEXT ) {
            uri.path = "$contextPath/manage/persons/put"
            uri.query = [ id : 0, x : 100, y : 100 ]
            response.success = response.failure = { resp, data ->
                result.statusCode = resp.statusLine.statusCode
                if( data instanceof InputStreamReader ) {
                    result.data = data.text
                }
                else {
                    result.data = data
                }
            }
        }
        then:
        result.statusCode == SC_OK
        result.data == 'Person( id = 0, x = 100.0, y = 100.0, dx = 1.0, dy = 1.0 )'
    }

    def 'test add person without coords'() {
        when:
        def result = [:]
        conn.request( GET, TEXT ) {
            uri.path = "$contextPath/manage/persons/put"
            uri.query = [ id : 0 ]
            response.success = response.failure = { resp, data ->
                result.statusCode = resp.statusLine.statusCode
                if( data instanceof InputStreamReader ) {
                    result.data = data.text
                }
                else {
                    result.data = data
                }
            }
        }
        then:
        result.statusCode == SC_OK
        "${result.data}".startsWith "Person( id = 0";
    }

    def 'test get person'() {
        setup:
        conn.request( GET ) {
            uri.path = "$contextPath/manage/persons/put"
            uri.query = [ id : 0, x : 100, y : 100 ]
        }
        when:
        def result = [:]
        conn.request( GET, TEXT ) {
            uri.path = "$contextPath/manage/persons/get"
            uri.query = [ id : "0" ]
            response.success = response.failure = { resp, data ->
                result.statusCode = resp.statusLine.statusCode
                if( data instanceof InputStreamReader ) {
                    result.data = data.text
                }
                else {
                    result.data = data
                }
            }
        }
        then:
        result.statusCode == SC_OK
        "${result.data}".startsWith "Person( id = 0";
    }
}
