package ru.job4j.url_shortcut.utils.devtools;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Customized {@code System.out.println()} && {@code System.err.println()}
 * It Made for help in develop and debugging, "Savior Prints".
 * Features:
 * * Smart printing collections - pretty print List, Map, String, Array.
 * * Message template - easy change message template. (*special_word* - it is place for some values from input).
 * * Pretty print collections of collections. Example: List<List<String>>
 *
 * @author Daiils Loputevs(laiwiense@gmail.com)
 * @version 1.1.
 * @since 03.02.2021.
 */
public class DevLog {
    
    /* Printing templates */
    
    private static final String DEV_LOG_INF = "DEV : [*place*] -- *info*";
    private static final String DEV_LOG_VAR = "DEV : [*place*] -- *info* :: *obj*";
    
    private static final String ELEM_STORE_NAME = "DEV : [*place*] -- Print *store* :: *name*";
    private static final String ITERABLE_ELEM = "DEV : [*place*] -- *index* :: *elem*";
    private static final String MAP_ELEM = "DEV : [*place*] -- *index* :: key=*key* ### val=*val*";
    
    
    /* Public API */
   
    
    /**
     * Print message to console.
     *
     * @param place - place marker, indicate where this print is using.
     * @param info  - information, show that happened.
     */
    public static void print(String place, String info) {
        System.out.println(makeInfoMsg(place, info));
    }
    
    /**
     * Print message to Error console.
     * * Usually it using just for "Red colour" printing, cause it different from "usual color"
     *
     * @param place - place marker, indicate where this print is using.
     * @param info  - information, show that happened.
     */
    public static void printErr(String place, String info) {
        System.err.println(makeInfoMsg(place, info));
    }
    
    /**
     * Print variable to console.
     *
     * @param place    - place marker, indicate where this print is using.
     * @param varName  - printing variable name.
     * @param variable - printing variable.
     */
    public static void print(String place, String varName, Object variable) {
        System.out.println(printStrategy(place, varName, variable));
    }
    
    /**
     * Print variable to console.
     * * Usually it using just for "Red colour" printing, cause it different from "usual color"
     *
     * @param place    - place marker, indicate where this print is using.
     * @param varName  - printing variable name.
     * @param variable - printing variable.
     */
    public static void printErr(String place, String varName, Object variable) {
        System.err.println(printStrategy(place, varName, variable));
    }
    
    
    /* Inner structure */
    
    
    private static String printStrategy(String place, String info, Object obj) {
        if (obj instanceof List) {
            return makeListMsg(place, info, (List<Object>) obj);
        } else if (obj instanceof Map) {
            return makeMapMsg(place, info, (Map<Object, Object>) obj);
        } else if (obj instanceof Object[]) {
            return makeArrayMsg(place, info, (Object[]) obj);
        } else {
            return makeObjectMsg(place, info, obj);
        }
    }
    
    
    private static String makeInfoMsg(String place, String info) {
        return DEV_LOG_INF
                .replace("*place*", upperCasePlace(place))
                .replace("*info*", info);
    }
    
    private static String makeObjectMsg(String place, String info, Object obj) {
        return DEV_LOG_VAR
                .replace("*place*", upperCasePlace(place))
                .replace("*info*", info)
                .replace("*obj*", prettyWrapObject(obj));
    }
    
    private static String makeArrayMsg(String place, String arrayName, Object[] arr) {
        var rsl = new StringJoiner(System.lineSeparator());
        place = upperCasePlace(place);
        
        rsl.add(ELEM_STORE_NAME
                .replace("*place*", place)
                .replace("*store*", "Array")
                .replace("*name*", arrayName)
        );
        if (arr.length != 0) {
            String localTemplate = ITERABLE_ELEM.replace("*place*", place);
            int elemIndex = 0;
            for (Object obj : arr) {
                rsl.add(localTemplate
                        .replace("*index*", String.valueOf(elemIndex++))
                        .replace("*elem*", prettyWrapObject(obj))
                );
            }
        }
        return rsl.toString();
    }
    
    private static String makeListMsg(String place, String listName, List<Object> list) {
        var rsl = new StringJoiner(System.lineSeparator());
        place = upperCasePlace(place);
        
        rsl.add(ELEM_STORE_NAME
                .replace("*place*", place)
                .replace("*store*", "List")
                .replace("*name*", listName)
        );
        if (!list.isEmpty()) {
            String localTemplate = ITERABLE_ELEM.replace("*place*", place);
            int elemIndex = 0;
            for (Object obj : list) {
                rsl.add(localTemplate
                        .replace("*index*", String.valueOf(elemIndex++))
                        .replace("*elem*", prettyWrapObject(obj))
                );
            }
        }
        return rsl.toString();
    }
    
    private static String makeMapMsg(String place, String mapName, Map<Object, Object> map) {
        var rsl = new StringJoiner(System.lineSeparator());
        
        rsl.add(ELEM_STORE_NAME
                .replace("*place*", upperCasePlace(place))
                .replace("*store*", "Map")
                .replace("*name*", mapName)
        );
        if (!map.isEmpty()) {
            String localTemplate = MAP_ELEM.replace("*place*", place);
            int elemIndex = 0;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                rsl.add(localTemplate
                        .replace("*index*", String.valueOf(elemIndex++))
                        .replace("*key*", prettyWrapObject(entry.getKey()))
                        .replace("*val*", prettyWrapObject(entry.getValue()))
                );
            }
        }
        return rsl.toString();
    }
    
    /**
     * Need for Easy extend object formatting, just add {@code else if}.
     * *not for List, it's for <elementType>.
     */
    private static String prettyWrapObject(Object obj) {
        if (obj == null) {
            return "null";
        } else if (obj instanceof String) {
            return "\"" + obj + "\"";
        } else {
            return obj.toString();
        }
    }
    
    private static String upperCasePlace(String place) {
        return place.substring(0, 1).toUpperCase() + place.substring(1);
    }
    
}
