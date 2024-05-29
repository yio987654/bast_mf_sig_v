package com.lanf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.hc.client5.http.fluent.Request;

public class yiotest {

    public static void main(final String... args) throws Exception {
        System.out.println(1);
//        String apiUrl = "https://api.zenrows.com/v1/?apikey=a15467e741461766959eff3fb2fb206834b9b53e&url=https%3A%2F%2Fphoton-sol.tinyastro.io%2Fapi%2Ftrending%3Fbuys_from%3D%26buys_to%3D%26freeze_authority%3Dfalse%26lp_burned_perc%3Dfalse%26mint_authority%3Dfalse%26mkt_cap_from%3D2000%26mkt_cap_to%3D20000000000000000000%26one_social%3Dfalse%26order_by%3Dcreated_at%26order_dir%3Dasc%26period%3D1m%26sells_from%3D%26sells_to%3D%26top_holders_perc%3Dfalse%26txns_from%3D30%26txns_to%3D%26usd_liq_from%3D4000%26usd_liq_to%3D%26volume_from%3D5000%26volume_to%3D&premium_proxy=true&custom_headers=true";
//        String response = Request.get(apiUrl).addHeader("Cookie", "wallet.address=eyJfcmFpbHMiOnsibWVzc2FnZSI6IklrVXhiMlpxZFdGMGRXcE9aR1p2VWtWWFZXc3pVV2czZWpWV04xQlJWR28xVVZFNVVsaENhV280WnpscUlnPT0iLCJleHAiOiIyMDI0LTA1LTA4VDE3OjA2OjIxLjAwMFoiLCJwdXIiOiJjb29raWUud2FsbGV0LmFkZHJlc3MifX0%3D--092cb9d0cc4b820bc20e05343e836b0ced0c8a13; _photon_eth_production=3O42p6gHIfCP5m8owbj2PQQji1qZyMWLXaaW2FN2zV8IgwLVhDmEOQzCWoNOrCSZOaPbmnylJAsqzGzUhmXq%2FJSYZHwrROzkYNX%2B6C0uOoOH4ArQohhvrAl5hZSy0vhT7qhqk2eHCFb8BhvNumrtdn2wkpLh%2FcXN0Jx3jkXuMVTJ4UP28MlSX8O7aMlnwTY6EpiWQf9e7eMizDrBsWkiOe1N7Vd1fk4DEPhaO7v8aB5c223NplvOmdImPX9yiz6Mt4P6EGHBp%2FA3wIpmqcq4kmdzg4T5IHI8YfvwoS4zKLy3f2NGPJq8kvxngf8UuGe%2BJauenA%3D%3D--oBZsafQYSfBlEQU5--7q9NVhwv98kS6vB1%2Btw2AQ%3D%3D; trid=5cf8a422f970faf5de45452156f27e0d; cf_clearance=cKSt3p6ooucxUtf3OgGBocbf5fR_wDGrqFIu65uifXI-1713965688-1.0.1.1-3YDb0mmwjBZ_E3J15Nz3MiERaZt5I8bMqZe2XzLXYLg9vilQHClhrXC2fYjP_IeztlroUYqX76A3MJC9JZj2XA; __cf_bm=dnsTC.vurLcf3Ry8TuHJBsjCGdFHc3WU7k5NOCXhknY-1713966722-1.0.1.1-TA9ZaEaxEC6mvymaCv3VVOsrVZ9EoMwR5UntUHMfz81vw.TQrkUI7dsMa0t6diSbrhd53XKeWL7_M0VTZGMI9A; _photon_ta=xgCKdG6%2FfIMBcN2d7buuQbCSGeHWFM%2B1ZJergI3ndvmfAR1SS8KtKuXF5L%2FuegG5b0C%2BcAo9bcNMt514nzmdiDiUqtKCyTagenekmWOOIQs%2F77Kap9iJjLSX3MGByawn88d%2B9spkNOrYUgDzwcPnXGjTzNa1TRxwvkffveH3X487wMeqLVAm%2FzAI%2B9SZUVs0q3Tlnb9dvP2ccw%2Fm2jWTaFEBCJewK3v1heL%2Bnuc5%2Fs5dB4RueqlqRgG0tdCTxxvfG1t2nU0UzafBlLBlHrSCDY6Xl3U0P4ZlKrYtoO36anLD2t7%2FHhjVKqSe4ut4SGJVHuERhh45DlT9QEdm8OOMw62cNQQkxEr7M3j1oc5zGj0rIvbDayDDRS2VpMWxu8ObFjtfSvNC8%2FDdh3GhboXHKOcUzGRyS1H6dnxG8JesTzEf7M5aBK8YqQI72lLLkpNcK8CHdD8Ed2RhBugDLPS9USLj6v%2Fx0WTX5r2pNtTRhGIe9wb6kOD%2FimmHMPbm5%2BbfcdGITygY1%2BHhrOaJKGVxrmGxM5kKLc83kK7dD%2Bs0qFkiaPt7XjW6P6Ymqoo%2FUjWmI4pEgNlqvu2dcDu1j3CEq9g2Lk%2Fb%2BTkPa8atqXElnZNr8jSeKoq6f5tqw%2FbCVeTMuNsbD7mRY3Dqkv7deEJHWBB%2FczLdUQAS3Oa0YQwylhuWFYB41%2Frb%2F6PfjbMgkg1ojWxLIODL5rc%3D--stb3ooOTvjzdkUNU--Ss5BX8ebpPAlk3ihbTiFQA%3D%3D").execute().returnContent().asString();
//        System.out.println("yioxbjsbcxhbdhjcs");
//        JSONObject parse = JSON.parseObject(response, JSONObject.class);
//        JSONArray data = (JSONArray) parse.get("data");
//        for (Object datum : data) {
//
//        }
//        data.get(0).toString();
//        System.out.println(response);
    }

}
