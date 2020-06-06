<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class JobDetail extends REST_Controller{
		public function __construct()
		{
			parent::__construct();
			$this->load->model('Job_model','job');
		}
		public function index_get(){
			$id = $this->get('JobID');
			$job = $this->job->jobDetail($id);
			if($job){
				$this->response([
					'status' => 200,
					'message' => '',
					'data' => $job
                ], REST_Controller::HTTP_OK);
			}
			else{
				$this->response([
					'status' => 200,
					'message' => '',
					'data' => null

					//$company
                ], REST_Controller::HTTP_OK);
			}
		}
	}
?>
