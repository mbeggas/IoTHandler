package computer.iothandler;

public class ComputerTools {

	public Long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}
	public Long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}
	public Integer getProcessorCores() {
		return Runtime.getRuntime().availableProcessors();
	}
}
