<template>
    <view>
        <view class="address-container">
            <view class="from">
                <text>{{ from.address }}</text>
            </view>
            <view class="dashed-line"></view>
            <view class="to">
                <text>{{ to.address }}</text>
            </view>
        </view>
        <map id="map" :longitude="from.longitude" :latitude="from.latitude" :style="contentStyle" scale="12"
            :enable-traffic="false" :show-location="true" class="map" :polyline="polyline" :markers="markers"></map>

        <view class="panel">
            <view class="row">
                <view class="info">
                    <view class="label">全程</view>
                    <view class="value">
                        <text>{{ distance }}</text>
                        公里
                    </view>
                    <view class="label">，预计</view>
                    <view class="value">
                        <text>{{ duration }}</text>
                        分钟
                    </view>
                </view>
                <view class="opt" @tap="chooseCarHandle" v-if="!showCar">选择车辆</view>
                <view class="opt" @tap="chooseCarHandle" v-if="showCar">{{ carPlate }}</view>
            </view>
            <button class="btn" @tap="createOrderHandle">确定下单</button>
        </view>
        <u-popup v-model="showPopup" mode="center" width="600" height="350" :mask-close-able="false">
            <view class="popup-title">您的订单正在等待司机接单</view>
            <view class="count-down">
                <u-count-down ref="uCountDown" :timestamp="timestamp" :autoplay="false" separator="zh"
                    :show-hours="false" :show-border="true" bg-color="#DDF0FF" separator-color="#0083F3"
                    border-color="#0D90FF" color="#0D90FF" font-size="32" @end="countEndHandle"
                    @change="countChangeHandle"></u-count-down>
            </view>
            <button class="btn" @tap="cancelHandle">取消订单</button>
        </u-popup>
    </view>
</template>

<script>
    let QQMapWX = require('../../lib/qqmap-wx-jssdk.min.js');
    let qqmapsdk;
    export default {
        data() {
            return {
                from: {
                    address: '',
                    longitude: 0,
                    latitude: 0
                },
                to: {
                    address: '',
                    longitude: 0,
                    latitude: 0
                },
                contentStyle: '',
                windowHeight: 0,
                distance: 0,
                duration: 0,
                polyline: [],
                markers: [],
                infoStatus: true,
                carId: null,
                carPlate: null,
                carType: null,
                showCar: false,
                showPopup: false,
                timestamp: 15 * 60,
                orderId: null
            };
        },
        methods: {
            formatPolyline(polyline) {
                let coors = polyline;
                let pl = [];
                //坐标解压（返回的点串坐标，通过前向差分进行压缩）
                const kr = 1000000;
                for (let i = 2; i < coors.length; i++) {
                    coors[i] = Number(coors[i - 2]) + Number(coors[i]) / kr;
                }
                //将解压后的坐标放入点串数组pl中
                for (let i = 0; i < coors.length; i += 2) {
                    pl.push({
                        longitude: coors[i + 1],
                        latitude: coors[i]
                    });
                }
                return pl;
            },
            calculateLine: function(ref) {
                qqmapsdk.direction({
                    mode: 'driving',
                    from: {
                        latitude: ref.from.latitude,
                        longitude: ref.from.longitude
                    },
                    to: {
                        latitude: ref.to.latitude,
                        longitude: ref.to.longitude
                    },
                    success: function(resp) {
                        if (resp.status != 0) {
                            uni.showToast({
                                icon: 'error',
                                title: resp.message
                            });
                            return;
                        }
                        let route = resp.result.routes[0];
                        let distance = route.distance;
                        let duration = route.duration;
                        let polyline = route.polyline;
                        ref.distance = Math.ceil((distance / 1000) * 10) / 10;
                        ref.duration = duration;
                        let points = ref.formatPolyline(polyline);

                        ref.polyline = [{
                            points: points,
                            width: 6,
                            color: '#05B473',
                            arrowLine: true
                        }];
                        ref.markers = [{
                                id: 1,
                                latitude: ref.from.latitude,
                                longitude: ref.from.longitude,
                                width: 25,
                                height: 35,
                                anchor: {
                                    x: 0.5,
                                    y: 0.5
                                },
                                iconPath: 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/start.png'
                            },
                            {
                                id: 2,
                                latitude: ref.to.latitude,
                                longitude: ref.to.longitude,
                                width: 25,
                                height: 35,
                                anchor: {
                                    x: 0.5,
                                    y: 0.5
                                },
                                iconPath: 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/end.png'
                            }
                        ];
                    }
                });
            },


        },
        onLoad: function(options) {
            let that = this;
            //设置地图控件的高度适配屏幕高度
            let windowHeight = uni.getSystemInfoSync().windowHeight;
            that.windowHeight = windowHeight;
            that.contentStyle = `height:${that.windowHeight}px;`;


        },
        onShow: function() {
            let that = this;
            that.calculateLine(that);
        }
    };
</script>

<style lang="less">
    @import url('create_order.less');
</style>