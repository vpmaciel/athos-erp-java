package erp.aop.gui;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Container;
import java.util.HashSet;

@SuppressWarnings("serial")
public final class FocusTabListener extends Component {

	private final HashSet<AWTKeyStroke> backup;
	private final HashSet<AWTKeyStroke> conj;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FocusTabListener(Container container) {
		this.backup = new HashSet<AWTKeyStroke>(container.getFocusTraversalKeys(0));
		this.conj = (HashSet) this.backup.clone();
		this.conj.add(AWTKeyStroke.getAWTKeyStroke(10, 0));
		container.setFocusTraversalKeys(0, this.conj);
	}
}
