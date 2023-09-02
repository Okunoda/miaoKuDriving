<template>
	<el-dialog
		:title="!dataForm.id ? '新增' : '修改'"
		v-if="isAuth(['ROOT', 'VOUCHER:INSERT', 'VOUCHER:UPDATE'])"
		:close-on-click-modal="false"
		v-model="visible"
		width="500px"
		top="8vh"
	>
		<el-form :model="dataForm" ref="dataForm" :rules="dataRule" label-width="100px">
			<el-form-item label="代金券名称" prop="name">
				<el-input v-model="dataForm.name" size="medium" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="描述信息" prop="remark">
				<el-input
					v-model="dataForm.remark"
					size="medium"
					type="textarea"
					rows="3"
					style="width:100%"
					clearable
				/>
			</el-form-item>
			<el-form-item label="标签" prop="tag">
				<el-input v-model="dataForm.tag" size="medium" style="width:100%" clearable />
			</el-form-item>
			<el-form-item label="总数量" prop="totalQuota">
				<el-input-number v-model="dataForm.totalQuota" :min="0" :step="100" style="width:55%" size="small" />
				&nbsp;&nbsp;&nbsp;/&nbsp;张
			</el-form-item>
			<el-form-item label="代金券面额" prop="discount">
				<el-input-number v-model="dataForm.discount" :min="1" :max="50" style="width:55%" size="small" />
				&nbsp;&nbsp;&nbsp;/&nbsp;元
			</el-form-item>
			<el-form-item label="最低消费额" prop="withAmount">
				<el-input-number
					v-model="dataForm.withAmount"
					:min="0"
					:max="50"
					:step="10"
					style="width:55%"
					size="small"
				/>
				&nbsp;&nbsp;&nbsp;/&nbsp;元
			</el-form-item>
			<el-form-item label="赠送类型" prop="type">
				<el-select
					v-model="dataForm.type"
					class="input"
					placeholder="赠送类型"
					size="medium"
					clearable="clearable"
				>
					<el-option label="用户领取" value="1" />
					<el-option label="注册赠券" value="2" />
					<el-option label="补偿券" value="3" />
				</el-select>
			</el-form-item>
			<el-form-item label="领取限制" prop="limitQuota">
				<el-select
					v-model="dataForm.limitQuota"
					class="input"
					placeholder="领取限制"
					size="medium"
					clearable="clearable"
				>
					<el-option label="无限制" value="0" />
					<el-option label="有限制" value="1" />
				</el-select>
			</el-form-item>
			<el-form-item label="有效期限" prop="timeType">
				<el-select
					v-model="dataForm.timeType"
					class="input"
					placeholder="有效期限"
					size="medium"
					clearable="clearable"
					@change="changeHandle"
					@clear="clearHandle"
				>
					<el-option label="无期限" value="0" />
					<el-option label="有效天数" value="1" />
					<el-option label="有效日期" value="2" />
				</el-select>
			</el-form-item>
			<el-form-item label="有效天数" prop="days" v-if="daysVisible">
				<el-input-number v-model="dataForm.days" :min="1" style="width:55%" size="small" />
			</el-form-item>
			<el-form-item label="有效日期" prop="date" v-if="dateVisible">
				<el-date-picker
					v-model="dataForm.date"
					type="daterange"
					range-separator="~"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
					size="medium"
				></el-date-picker>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button size="medium" @click="visible = false">取消</el-button>
				<el-button type="primary" size="medium" @click="dataFormSubmit">确定</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script>
export default {
	data: function() {
		return {
			visible: false,
			dataForm: {
				name: null,
				remark: null,
				tag: null,
				totalQuota: 0,
				discount: null,
				withAmount: null,
				type: null,
				limitQuota: null,
				timeType: null,
				days: null,
				date: []
			},
			dataRule: {
				name: [{ required: true, message: '名称不能为空' }],
				type: [{ required: true, message: '赠送类型不能为空' }],
				limitQuota: [{ required: true, message: '领取限制不能为空' }],
				timeType: [{ required: true, message: '有效期限不能为空' }],
				date: [{ validator: this.validateDate }]
			},
			daysVisible: false,
			dateVisible: false
		};
	},

	methods: {
		
	}
};
</script>

<style lang="less" scoped="scoped"></style>
