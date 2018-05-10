<c:choose>
	<c:when test="${approval.key eq 'wf_purchaseContract' }">
		<%@ include file="../detail/contractDetail.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_saleContract' }">
		<%@ include file="../detail/saleContract.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_paymentConfirm' }">
		<%@ include file="../detail/paymentConfirm.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_payApply' }">
		<%@ include file="../detail/payApply.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_saleDelivery' }">
		<%@ include file="../detail/saleDelivery.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_orderShipContract' }">
		<%@ include file="../detail/orderShipContractDetail.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_sealApproval' }">
		<%@ include file="../detail/sealApproval.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_saleSettlement' }">
		<%@ include file="../detail/saleSettlement.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_saleInvoice' }">
		<%@ include file="../detail/saleInvoice.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_logisticShipments' }">
		<%@ include file="../detail/shipmentsDetail.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_logisticWharf' }">
		<%@ include file="../detail/wharfDetail.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_highwayContract' }">
		<%@ include file="../detail/highwayContract.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_railwayContract' }">
		<%@ include file="../detail/railwayContract.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_portContract' }">
		<%@ include file="../detail/portContract.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_freightLetter' }">
		<%@ include file="../detail/freightLetter.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_inputInvoice' }">
		<%@ include file="../detail/inputInvoice.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_logisticTransport' }">
		<%@ include file="../detail/transport.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_lhInsuranceContract' }">
		<%@ include file="../detail/insuranceContract.jsp"%>
	</c:when>
	<c:when test="${approval.key eq 'wf_proxyAgreement' }">
		<%@ include file="../detail/proxyAgreement.jsp"%>
	</c:when>
</c:choose>
<%@ include file="../detail/accessory.jsp"%>
<%@ include file="../detail/system.jsp"%>