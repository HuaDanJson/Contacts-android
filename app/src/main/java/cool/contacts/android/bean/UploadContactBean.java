package cool.contacts.android.bean;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import cn.bmob.v3.BmobObject;
import cool.contacts.android.utils.Cn2Spell;

public class UploadContactBean extends BmobObject implements Comparable<UploadContactBean> {

    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母
    private boolean isContainsEmoji;

    public UploadContactBean(String currentUserName, String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userName = currentUserName;
        char codePoint = name.charAt(0);

        LogUtils.d("MainActivity UploadContactBean name = " + name + "   codePoint =  " + codePoint);
        if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
            isContainsEmoji = true;
        } else {
            isContainsEmoji = false;
        }
        LogUtils.d("MainActivity UploadContactBean name = " + name + "   isContainsEmoji =  " + isContainsEmoji);

        if (isContainsEmoji) {
            pinyin = "A11111111" + Cn2Spell.getPinYin(name); // 根据姓名获取拼音
            firstLetter = "A";
        } else {
            pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
            firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
            if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                firstLetter = "#";
            }
        }
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public boolean isContainsEmoji() {
        return isContainsEmoji;
    }

    public void setContainsEmoji(boolean containsEmoji) {
        isContainsEmoji = containsEmoji;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(@NonNull UploadContactBean another) {
        if (firstLetter.equals("#") && !another.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && another.getFirstLetter().equals("#")) {
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(another.getPinyin());
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UploadContactBean{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                ", isContainsEmoji=" + isContainsEmoji +
                '}';
    }
}