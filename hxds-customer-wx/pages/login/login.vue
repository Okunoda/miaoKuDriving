<template>
	<view>
		<image src="../../static/login/top.png" mode="widthFix" class="top"></image>
		<image src="../../static/login/logo.png" mode="widthFix" class="logo"></image>
		<view class="desc">
			<text class="name">（乘客端）</text>
			<text class="version">Ver20XX.201</text>
		</view>
		<button class="btn" @tap="login">微信登陆</button>
		<view class="register-container">
			没有账号?
			<text class="link" @tap="register">立即注册</text>
		</view>
		<text class="remark">本小程序仅限于华夏代驾客户使用，新用户必须先注册方可在本小程序中下单。</text>
		<u-toast ref="uToast" />
	</view>
</template>

<script>
export default {
	data() {
		return {
			code: null
		};
	},
	methods: {
		
	},
	onLoad: function() {}
};
</script>

<style lang="less">
@import url('login.less');
</style>
