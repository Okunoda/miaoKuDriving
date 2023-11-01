<template>
  <view>
    <image src="../../static/login/top.png" mode="widthFix" class="top"></image>
    <image src="../../static/login/logo.png" mode="widthFix" class="logo"></image>
    <view class="desc">
      <text class="name">（司机端）</text>
      <text class="version">Ver20XX.201</text>
    </view>
    <button class="btn" @tap="login()">微信登陆</button>
    <view class="register-container">
      没有账号?
      <text class="link" @tap="toRegisterPage()">立即注册</text>
    </view>
    <text class="remark">本小程序仅限于华夏代驾签约司机使用，新司机必须先注册，经由实名认证之后方可在本小程序中接单运营。
    </text>
    <u-toast ref="uToast"/>
  </view>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    toRegisterPage: function () {
      uni.navigateTo({
        url: '../register/register'
      });
    },
    login: function () {
      let that = this;
      uni.login({
        provider: 'weixin',
        success: function (resp) {
          let code = resp.code;
          let data = {
            code: code
          };

          that.ajax(that.url.login, 'POST', data, function (resp) {
            // console.log(resp)
            if (!resp.data.hasOwnProperty('token')) {
              that.$refs.uToast.show({
                title: '请先注册',
                type: 'error'
              });
            } else {
              let token = resp.data.token;
              let realAuth = resp.data.realAuth;
              let archive = resp.data.archive;
              uni.setStorageSync('token', token);
              uni.setStorageSync('realAuth', realAuth);
              //删除缓存的执行订单数据，进入工作台重新加载数据
              uni.removeStorageSync('executeOrder');
              that.$refs.uToast.show({
                title: '登陆成功',
                type: 'success',
                callback: function () {
                  uni.setStorageSync('workStatus', '停止接单');
                  //检查用户是否没有填写实名信息
                  if (realAuth == 1) {
                    //跳转到填写实名信息页面
                    uni.redirectTo({
                      url: '../../identity/filling/filling?mode=create'
                    });
                  }
                  //检查系统是否存有司机的面部数据
                  else if (archive == false) {
                    uni.showModal({
                      title: '提示消息',
                      content: '您还没有录入用于核实身份的面部特征信息，如果不录入将无法接单',
                      confirmText: '录入',
                      cancelText: '取消',
                      success: function (resp) {
                        if (resp.confirm) {
                          //跳转到面部识别页面，采集人脸数据
                          uni.redirectTo({
                            url: '../../identity/face_camera/face_camera?mode=create'
                          });
                        } else {
                          uni.switchTab({
                            url: '../workbench/workbench'
                          });
                        }
                      }
                    });
                  } else {
                    uni.switchTab({
                      url: '../workbench/workbench'
                    });
                  }
                }
              });
            }
          });
        }
      });
    }
  },
  onLoad: function () {

  }
};
</script>

<style lang="less">
@import url('login.less');
</style>