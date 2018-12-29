package lichKing.client.datasource;

import com.smartgwt.client.types.ValueEnum;

/**
 * 常用的操作类型字符串，可映射多语言
 * @author catPan
 *
 */
public enum OpTypeEnum  implements ValueEnum {

	Refresh("Refresh"),
	Add("Add"),
	Look("Look"),
	Modify("Modify"),
	Delete("Delete"),
	Disabled("Disabled"),
	Enable("Enable"),
	Save("Save");

    private String value;

    OpTypeEnum(String value) {
        this.value = value;
    }
    
	@Override
	public String getValue() {
        return this.value;
	}
	/**
	 * 获取对应操作按钮的图片路径
	 * @return
	 */
	public String getOpImgSrc(){
		return "op/"+this.value+".png";
	}	

}
