<template>
    <view class="page">
        <u-cell-group :border="false">
            <u-cell-item icon="info-circle-fill" title="车型信息" :value="carType" @click="carTypeHandle"></u-cell-item>
            <u-cell-item icon="info-circle-fill" title="车牌号码" :value="carPlate" @click="carPlateHandle"></u-cell-item>
        </u-cell-group>
        <button class="btn" @tap="saveHandle">确定保存</button>
        <view class="desc">
            保存即代表您同意华夏代驾的
            <text>《隐私政策》</text>
        </view>
        <u-toast ref="uToast" />
    </view>
</template>

<script>
export default {
    data() {
        return {
            carType: '',
            carPlate: ''
        };
    },
    methods: {
        
    }
};
</script>

<style lang="less">
@import url('add_car.less');
</style>
