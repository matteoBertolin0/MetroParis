package it.polito.tdp.metroparis.model;

public class CoppiaId {
	
	private int id_partenza, id_arrivo;

	public CoppiaId(int id_partenza, int id_arrivo) {
		this.id_partenza = id_partenza;
		this.id_arrivo = id_arrivo;
	}

	public int getId_partenza() {
		return id_partenza;
	}

	public void setId_partenza(int id_partenza) {
		this.id_partenza = id_partenza;
	}

	public int getId_arrivo() {
		return id_arrivo;
	}

	public void setId_arrivo(int id_arrivo) {
		this.id_arrivo = id_arrivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_arrivo;
		result = prime * result + id_partenza;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppiaId other = (CoppiaId) obj;
		if (id_arrivo != other.id_arrivo)
			return false;
		if (id_partenza != other.id_partenza)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CoppiaId [id_partenza=" + id_partenza + ", id_arrivo=" + id_arrivo + "]";
	}
	
	
	
}
