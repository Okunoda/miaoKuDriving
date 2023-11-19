<template>
	<view class="page">
		<view class="summary-container">
			<image :src="photo" mode="widthFix" class="photo"></image>
			<view class="summary">
				<view class="row">
					<text class="name">{{ name }}</text>
					<image
						:src="realAuth ? '../../static/mine/icon-1.png' : '../../static/mine/icon-2.png'"
						mode="widthFix"
						class="auth-icon"
					></image>
					<text :class="realAuth ? 'auth' : 'unauth'">{{ realAuth ? '已认证' : '未认证' }}</text>
				</view>
				<view class="row">
					<view class="attribute">
						<view class="item">代驾：{{ years }}年</view>
						<view class="item">等级：Lv{{ level }}</view>
						<view class="item">接单：{{ changeNumber(totalOrder) }}</view>
					</view>
				</view>
			</view>
		</view>
		<view class="notice-container">
			<view class="notice-title">
				<image src="../../static/mine/icon-3.png" mode="widthFix"></image>
				<text>{{ levelName }}</text>
			</view>
			<view class="desc">升级Lv{{ level + 1 }}等级还需接单{{ 50 - (totalOrder % 50) }}个，并且为顾客提供安全和高质量代驾服务</view>
		</view>
		<view class="nav-container">
			<view class="row">
				<view class="item" @tap="toPage('../../wallet/recharge/recharge')">
					<image src="../../static/mine/icon-4.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">钱包</text>
						<text class="desc">总额{{ balance }}元</text>
					</view>
				</view>
				<view class="border-column"></view>
				<view class="item">
					<image src="../../static/mine/icon-5.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">订单</text>
						<text class="desc">本周有{{ weekOrder }}单</text>
					</view>
				</view>
			</view>
			<view class="border-row"></view>
			<view class="row">
				<view class="item">
					<image src="../../static/mine/icon-6.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">顾客评价</text>
						<text class="desc">近期{{ weekComment }}条好评</text>
					</view>
				</view>
				<view class="border-column"></view>
				<view class="item">
					<image src="../../static/mine/icon-7.png" mode="widthFix"></image>
					<view class="info">
						<text class="title">我的申诉</text>
						<text class="desc">正在申诉{{ appeal }}件</text>
					</view>
				</view>
			</view>
		</view>
		<view class="tab-container">
			<view class="item">
				<view class="ball ball-1"></view>
				<text>活动中心</text>
			</view>
			<view class="item">
				<view class="ball ball-2"></view>
				<text>积分商城</text>
			</view>
			<view class="item">
				<view class="ball ball-3"></view>
				<text>本月排名</text>
			</view>
			<view class="item">
				<view class="ball ball-4"></view>
				<text>我的数据</text>
			</view>
		</view>
		<u-cell-group :border="false">
			<u-cell-item
				icon="account-fill"
				:icon-style="icon"
				title="账号与安全"
				:border-top="false"
				@click="this.toPage('../../user/account/account')"
			/>
			<u-cell-item icon="server-fill" :icon-style="icon" title="在线客服" @click="serviceHandle" />
			<u-cell-item icon="trash-fill" :icon-style="icon" title="清理缓存" @click="clearHandle" />
			<u-cell-item
				icon="file-text-fill"
				:icon-style="icon"
				title="用户指南"
				:border-bottom="false"
				@click="this.toPage('../../rule/user_guide/user_guide')"
			/>
		</u-cell-group>
		<view class="exit"><button class="btn" @tap="logoutHandle()">退出系统</button></view>
		<u-top-tips ref="uTips"></u-top-tips>
	</view>
</template>

<script>
let dayjs = require('dayjs');
export default {
	data() {
		return {
			icon: {
				color: '#8A8B8F',
				'margin-top': '-1rpx'
			},
			name: '',
			photo: '',
			realAuth: false,
			years: 0,
			level: 0,
			levelName: '',
			balance: 0,
			totalOrder: 0,
			weekOrder: 0,
			weekComment: 0,
			appeal: 0
		};
	},
	methods: {
    logoutHandle: function () {
      let that = this;
      uni.vibrateShort({});

      uni.showModal({
        title: "提示信息",
        content: "确认是否注销登录？",
        success: function (resp) {
          if (resp.confirm) {
            that.ajax(that.url.logout, 'GET', null, function (resp) {
              uni.removeStorage("token");
              uni.removeStorage("realAuth");
              uni.showToast({
                title: "已退出系统",
                success: function () {
                  setTimeout(function () {
                    uni.redirectTo({
                      url: "../login/login"
                    });
                  }, 1500);
                }
              });
            });
          }
        }
      });
    },
    serviceHandle: function () {
      uni.vibrateShort({});
      uni.makePhoneCall({
        phoneNumber: '10086'
      });
    },
    clearHandle: function () {
      uni.vibrateShort({});
      uni.showModal({
        title: '提示消息',
        content: '清理本地缓存',
        success: function (resp) {
          if (resp.confirm) {
            uni.vibrateShort({});
            uni.showLoading({
              title: '执行中'
            });
            //删除Storage中不必要的缓存
            let cache = uni.getStorageInfoSync();
            for (let key of cache.keys) {
              if (key == 'token' || key == 'realAuth') {
                continue;
              }
              uni.removeStorageSync(key); //删除不必要的缓存
              console.log('删除Storage缓存成功');
            }

            //删除保存的临时文件
            uni.getSavedFileList({
              success: function (resp) {
                for (let one of resp.fileList) {
                  let path = one.filePath;
                  uni.removeSavedFile({
                    filePath: path,
                    success: function () {
                      console.log('缓存文件删除成功');
                    }
                  });
                }
              }
            });
            setTimeout(function () {
              uni.showToast({
                title: '清理完毕'
              });
            }, 500);
          }
        }
      });
    },

	},
	onShow: function() {
    let that = this;
    that.ajax(that.url.searchDriverBaseInfo, 'POST', null, function (resp) {
      // console.log(resp.data);
      let result = resp.data.result;
      that.name = result.name;
      that.photo = result.photo;
      that.realAuth = uni.getStorageSync('realAuth') == 1;

      let createTime = dayjs(result.createTime, 'YYYY-MM-DD');
      let current = dayjs();

      let years = current.diff(createTime, 'years');
      that.years = years;
      that.level = result.summary.level;
      if (that.level < 10) {
        that.levelName = '初级代驾';
      } else if (that.level < 30) {
        that.levelName = '中级代驾';
      } else if (that.level < 50) {
        that.levelName = '高级代驾';
      } else {
        that.levelName = '王牌代驾';
      }
      that.balance = result.balance;
      that.totalOrder = result.summary.totalOrder;
      that.weekOrder = result.summary.weekOrder;
      that.weekComment = result.summary.weekComment;
      that.appeal = result.summary.appeal;
    });
	},
	onHide: function() {
		
	}
};
</script>

<style lang="less">
@import url('mine.less');
</style>
