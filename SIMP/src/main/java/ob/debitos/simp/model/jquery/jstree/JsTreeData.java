package ob.debitos.simp.model.jquery.jstree;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsTreeData {
	@JsonProperty("data")
	List<JsTreeAttribute> data;
}
