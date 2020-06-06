<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class UpdateAppliment extends REST_Controller{

		public function __construct()
		{
			parent::__construct();
			$this->load->model('Job_model','job');
		}

		public function index_post(){
			$JobID = $this->post('JobID');
			$ApplimentID = $this->post('ApplimentID');
			$status = $this->post('ApplimentStatus');
			$job = $this->job->checkQuota($JobID);
			if($job>0 && $status=="Accepted"){
				if($this->job->updateAppliment($ApplimentID,$status)>0){
					$this->job->updateQuota($JobID);
					$this->response([
						'status' => 200,
						'message' => 'Successfuly accept',
					], REST_Controller::HTTP_OK );
				}
				else{
					$this->response([
						'status' => 200,
						'message' => 'Failed to update',
					], REST_Controller::HTTP_OK );
				}
			}
			else if(($job>=0 || $job<0) && $status=="Rejected"){
				if($this->job->updateAppliment($ApplimentID,$status)>0){
					$this->response([
						'status' => 200,
						'message' => 'Successfuly reject',
					], REST_Controller::HTTP_OK );
				}
				else{
					$this->response([
						'status' => 200,
						'message' => 'Failed to update',
					], REST_Controller::HTTP_OK );
				}
			}
			else if($job<=0 && $status=="Accepted"){
				$this->response([
					'status' => 200,
					'message' => 'Quota habis!',
                ], REST_Controller::HTTP_OK);
			}
		}
	}

?>
