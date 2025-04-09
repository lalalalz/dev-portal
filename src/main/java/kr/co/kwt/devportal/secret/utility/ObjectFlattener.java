package kr.co.kwt.devportal.secret.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ObjectFlattener {

    private final ObjectMapper objectMapper;

    public Map<String, Object> flattenMap(Object object) {
        JsonNode jsonNode = objectMapper.valueToTree(object);
        Map<String, Object> flattenedMap = new HashMap<>();
        flattenMap(jsonNode, "", flattenedMap);
        return flattenedMap;
    }

    private static void flattenMap(JsonNode jsonNode, String prefix, Map<String, Object> flattenedMap) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = prefix.isEmpty() ? field.getKey() : prefix + "." + field.getKey();
                JsonNode value = field.getValue();

                if (value.isObject()) {
                    flattenMap(value, key, flattenedMap);
                }
                else if (value.isArray()) {
                    // 배열 처리
                    for (int i = 0; i < value.size(); i++) {
                        JsonNode arrayElement = value.get(i);
                        String arrayKey = key + "[" + i + "]";

                        if (arrayElement.isObject() || arrayElement.isArray()) {
                            flattenMap(arrayElement, arrayKey, flattenedMap);
                        }
                        else {
                            flattenedMap.put(arrayKey, unwrapValue(arrayElement));
                        }
                    }
                }
                else {
                    flattenedMap.put(key, unwrapValue(value));
                }
            }
        }
        else if (jsonNode.isArray()) {
            // 최상위 레벨이 배열인 경우 처리
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode arrayElement = jsonNode.get(i);
                String arrayKey = prefix + "[" + i + "]";

                if (arrayElement.isObject() || arrayElement.isArray()) {
                    flattenMap(arrayElement, arrayKey, flattenedMap);
                }
                else {
                    flattenedMap.put(arrayKey, unwrapValue(arrayElement));
                }
            }
        }
        else {
            flattenedMap.put(prefix, unwrapValue(jsonNode));
        }
    }

    private static Object unwrapValue(JsonNode jsonNode) {
        if (jsonNode.isNull()) {
            return null;
        }
        else if (jsonNode.isTextual()) {
            return jsonNode.asText();
        }
        else if (jsonNode.isInt()) {
            return jsonNode.asInt();
        }
        else if (jsonNode.isLong()) {
            return jsonNode.asLong();
        }
        else if (jsonNode.isDouble()) {
            return jsonNode.asDouble();
        }
        else if (jsonNode.isBoolean()) {
            return jsonNode.asBoolean();
        }
        else {
            return jsonNode.toString();
        }
    }
}