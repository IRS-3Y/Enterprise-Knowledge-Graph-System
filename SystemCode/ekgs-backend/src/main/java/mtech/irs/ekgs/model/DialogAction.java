package mtech.irs.ekgs.model;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.Value;

import lombok.Data;

@Data
public class DialogAction {

	private String name;
	
	private Map<String, Object> params = new HashMap<>();
	
	public static DialogAction of(QueryResult result) {
		DialogAction action = new DialogAction();
		action.setName(result.getAction());
		result.getParameters().getFieldsMap().forEach((k, v) -> {
			Value val = (Value)v;
			double number = val.getNumberValue();
			if(number > 0) {
				//only accept integer number value
				action.getParams().put(k, String.valueOf(Double.valueOf(number).intValue()));
			}else {
				action.getParams().put(k, val.getStringValue());
			}
		});
		return action;
	}
}
