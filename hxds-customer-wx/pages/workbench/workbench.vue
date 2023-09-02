<template>
	<view>
		<map id="map" :longitude="longitude" :latitude="latitude" :style="contentStyle" scale="15" :enable-traffic="false" :show-location="true" :enable-poi="true" class="map">
			<cover-image class="location" src="../../static/workbench/location.png" @tap="returnLocationHandle()"></cover-image>
		</map>
		<view class="panel">
			<view class="from" @tap="chooseLocationHandle('from')">
				<text>{{ from.address }}</text>
			</view>
			<view class="dashed-line"></view>
			<view class="to" @tap="chooseLocationHandle('to')">
				<text>{{ to.address }}</text>
			</view>
		</view>
	</view>
</template>

<script>
const chooseLocation = requirePlugin('chooseLocation');
let QQMapWX = require('../../lib/qqmap-wx-jssdk.min.js');

export default {
	data() {
		return {
			from: {
				address: '',
				longitude: 0,
				latitude: 0
			},
			to: {
				address: '输入你的目的地',
				longitude: 0,
				latitude: 0
			},
			longitude: 116.397505,
			latitude: 39.908675,
			contentStyle: '',
			windowHeight: 0,
			map: null,
			flag: null
		};
	},
	methods: {
		
	},
	onShow: function() {
		
	},
	onHide: function() {
		
	},
	onLoad: function() {
		let that = this;
		let windowHeight = uni.getSystemInfoSync().windowHeight;
		that.windowHeight = windowHeight;
		that.contentStyle = `height:${that.windowHeight}px;`;
	},
	onUnload: function() {
		
	}
};
</script>

<style lang="less">
@import url('workbench.less');
</style>
