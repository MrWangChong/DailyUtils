# DailyUtils 常用工具类


# 使用

修改你的  `build.gradle`文件

```gradle

//root project
allprojects {
        repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
//module project
     dependencies {
            compile 'com.github.MrWangChong:DailyUtils:1.0.3'
    }
    
```

使用法放

```java


        //复制内容到粘贴板
        CopyUtils.copy(MainActivity.this, "复制到粘贴板的内容");
        //获取粘贴板内容
        CopyUtils.paste(MainActivity.this);

        //判断是不是手机号
        boolean isMobilePhone = PhoneUtils.isMobilePhone("1234567890");
        //判断是不是座机号
        boolean isTelephone = PhoneUtils.isTelephone("021-1545268");

        try {
            //设置10个未读消息，设置为0可清除
            BadgeUtils.getInstence().setBadge(MainActivity.this, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //检测是不是银行卡号
        boolean isBankCard = BankInfoUtils.checkBankCard("62220202001121747777");
        //获取银行信息
        String bankInfo = BankInfoUtils.getBankInfo("62220202001121747777");
        
        //18dp转成px
        DisplayUtils.dip2px(MainActivity.this, 18);
        //18px转成dp
        DisplayUtils.px2dip(MainActivity.this, 18);
        //18px转成sp
        DisplayUtils.px2sp(MainActivity.this, 18);
        //18sp转成px
        DisplayUtils.sp2px(MainActivity.this, 18);
```
