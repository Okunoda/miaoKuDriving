<template>
    <view class="page">
        <view v-if="list.length > 0" v-for="one in list" class="row" @tap="choseOneHandle(one.id, one.carPlate)" @longpress="removeHandle(one.id)">
            <view>
                <u-icon name="info-circle-fill" color="#2979ff" size="35" class="icon"></u-icon>
                <text class="car-type">{{ one.carType }}</text>
            </view>
            <text class="car-plate">{{ one.carPlate }}</text>
        </view>
        <image v-if="list.length == 0" src="../../static/car_list/none.jpg" mode="widthFix" class="none"></image>
        <button class="btn" @tap="addHandle">添加车辆</button>
        <u-toast ref="uToast" />
    </view>
</template>

<script>
export default {
    data() {
        return {
            list: []
        };
    },
    methods: {
        
    },
    onLoad: function() {

    }
};
</script>

<style lang="less">
@import url('car_list.less');
</style>
