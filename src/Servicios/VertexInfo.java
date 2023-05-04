package Servicios;

public class VertexInfo {

	int d,f;

	public VertexInfo() {
		this.d = this.f = -1;
	}

	public boolean isVisited(){
		return d != -1;
	}

	public boolean isFinished(){
		return f != -1;
	}

	public void clear(){
		this.d = this.f = -1;
	}

	@Override
	public String toString() {
		//return "[value=" + value + ", d=" + d + ", f=" + f + "]";
		return "[d=" + d + ", f=" + f + "]";
	}

}
