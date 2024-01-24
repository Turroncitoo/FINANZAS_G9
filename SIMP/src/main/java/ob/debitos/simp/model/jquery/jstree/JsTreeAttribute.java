package ob.debitos.simp.model.jquery.jstree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsTreeAttribute {
    @JsonProperty("id")
	String id;
    
    @JsonProperty("text")
	String text;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("icon")
	String icon;
	
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("children")
	Boolean children;
}
