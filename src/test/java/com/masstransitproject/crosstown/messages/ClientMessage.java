package com.masstransitproject.crosstown.messages;

import java.io.Serializable;

import com.masstransitproject.crosstown.ExternallyNamespaced;

@SuppressWarnings("serial")
public class ClientMessage implements Serializable, ExternallyNamespaced {
	private String _name;

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	@Override
	public String getExternalNamespace() {
		return "MassTransit.TestFramework.Examples.Messages";


	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
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
		ClientMessage other = (ClientMessage) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientMessage [_name=" + _name + "]";
	}

}
