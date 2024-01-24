package ob.debitos.simp.model.jquery.jstree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsTreeState {
	boolean selected;
	boolean opened;
	boolean disabled;
	boolean checked;
}
